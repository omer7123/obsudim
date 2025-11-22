package com.obsudim.mypsychologist.presentation.exercises.exercisePassingFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultRequestEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.SaveExerciseResultResponseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.SectionsExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSection
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSectionUiRes
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetExerciseDetailUseCase
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.SaveExerciseResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExercisePassingViewModel @Inject constructor(
    private val getExerciseDetailUseCase: GetExerciseDetailUseCase,
    private val saveExerciseResultUseCase: SaveExerciseResultUseCase
) : ViewModel() {
    private var _screenState =
        MutableStateFlow<ExercisePassingScreenState>(ExercisePassingScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    private val exerciseId = MutableStateFlow("")

    fun getExerciseStructure(id: String) {
        exerciseId.value = id
        viewModelScope.launch {
            getExerciseDetailUseCase(id).collect { res ->
                when (res) {
                    is Resource.Error<ExerciseDetailEntity> -> {
                        _screenState.value =
                            ExercisePassingScreenState.Error
                    }
                    Resource.Loading -> _screenState.value = ExercisePassingScreenState.Loading
                    is Resource.Success<ExerciseDetailEntity> -> {
                        val pages = res.data.pages

                        _screenState.value =
                            ExercisePassingScreenState.Content(
                                currentPage = 0,
                                pagesWithFields = pages,
                                currValue = pages.flatMap { it.sections }
                                    .map { it.toUiRes() }
                            )
                    }
                }
            }
        }
    }

    fun btnClickNext() {
        val currState = (screenState.value as ExercisePassingScreenState.Content)
        if(currState.currentPage == currState.pagesWithFields.size - 1){
            viewModelScope.launch {
                saveExerciseResultUseCase(result = ExerciseResultRequestEntity(exerciseId.value, currState.currValue)).collect {state->
                    when(state){
                        is Resource.Error<SaveExerciseResultResponseEntity> -> _screenState.value =
                            ExercisePassingScreenState.Error
                        Resource.Loading -> _screenState.value =
                            ExercisePassingScreenState.Loading
                        is Resource.Success<SaveExerciseResultResponseEntity> -> _screenState.value =
                            ExercisePassingScreenState.SuccessSave
                    }
                }
            }
        }else {
            _screenState.value = currState.copy(currentPage = currState.currentPage + 1)
        }
    }

    fun btnClickPrev() {
        val currState = (screenState.value as ExercisePassingScreenState.Content)
        if (currState.currentPage >= 1)
            _screenState.value = currState.copy(currentPage = currState.currentPage - 1)
        else
            _screenState.value = currState.copy(currentPage = 0)
    }

    fun textInputChange(textInput: TypeOfSectionUiRes.TextInputUiEntity){
        val currState = (screenState.value as ExercisePassingScreenState.Content)
        _screenState.value = currState.copy(
            currValue = currState.currValue.map { curr ->
                if (curr.id == textInput.id)
                    textInput
                else
                    curr
            }
        )
    }
    private fun SectionsExerciseEntity.toUiRes(): TypeOfSectionUiRes =
        when (type) {
            TypeOfSection.AddableList -> TypeOfSectionUiRes.AddableListUiEntity(id)
            TypeOfSection.TextInput   -> TypeOfSectionUiRes.TextInputUiEntity(id)
        }
}
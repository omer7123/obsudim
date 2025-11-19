package com.obsudim.mypsychologist.presentation.exercises.exercisePassingFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.SectionsExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSection
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSectionUiRes
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetExerciseDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExercisePassingViewModel @Inject constructor(
    private val getExerciseDetailUseCase: GetExerciseDetailUseCase
) : ViewModel() {
    private var _screenState =
        MutableStateFlow<ExercisePassingScreenState>(ExercisePassingScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    fun getExerciseStructure(id: String) {
        viewModelScope.launch {
            getExerciseDetailUseCase(id).collect { res ->
                when (res) {
                    is Resource.Error<ExerciseDetailEntity> -> _screenState.value =
                        ExercisePassingScreenState.Error

                    Resource.Loading -> _screenState.value = ExercisePassingScreenState.Loading
                    is Resource.Success<ExerciseDetailEntity> -> {
                        val pages = res.data.pages
                        _screenState.value =
                            ExercisePassingScreenState.Content(
                                currentPage = 1,
                                pagesWithFields = pages,
                                currValue = pages.flatMap { it.sections }
                                    .map { it.toUiRes() }
                            )
                    }
                }
            }
        }
    }

    private fun SectionsExerciseEntity.toUiRes(): TypeOfSectionUiRes =
        when (type) {
            TypeOfSection.AddableList -> TypeOfSectionUiRes.AddableListUiEntity(id)
            TypeOfSection.TextInput   -> TypeOfSectionUiRes.TextInputUiEntity(id)
        }
}
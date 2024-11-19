package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.InputIntExerciseEntity
import com.example.mypsychologist.domain.entity.InputItemExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailWithDelegateItem
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.TypeOfExercise
import com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases.GetExerciseDetailUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases.SaveThoughtDiaryUseCase
import com.example.mypsychologist.ui.exercises.cbt.InputExerciseDelegateItem
import com.example.mypsychologist.ui.exercises.cbt.IntDelegateItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewThoughtDiaryViewModel(
    private val saveThoughtDiaryUseCase: SaveThoughtDiaryUseCase,
    private val getExerciseDetailUseCase: GetExerciseDetailUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<NewThoughtDiaryScreenState> =
        MutableStateFlow(NewThoughtDiaryScreenState.Init)
    val screenState: StateFlow<NewThoughtDiaryScreenState>
        get() = _screenState.asStateFlow()

    private val resultExercise = ArrayList<ExerciseResultEntity>()


    fun getFields(id: String) {
        viewModelScope.launch {
            _screenState.value = NewThoughtDiaryScreenState.Loading

            getExerciseDetailUseCase(id).collect { resource ->
                when (resource) {
                    is Resource.Error -> _screenState.value =
                        NewThoughtDiaryScreenState.Error(resource.msg.toString())

                    Resource.Loading -> _screenState.value = NewThoughtDiaryScreenState.Loading
                    is Resource.Success -> {

                        resultExercise.addAll(resource.data.fields.map {
                            when (it.type) {
                                TypeOfExercise.NumberInput -> ExerciseResultEntity(
                                    fieldId = it.id,
                                    value = "50"
                                )

                                TypeOfExercise.TextInput -> ExerciseResultEntity(
                                    fieldId = it.id,
                                    value = ""
                                )
                            }

                        })
                        _screenState.value =
                            NewThoughtDiaryScreenState.Content(resource.data.toDelegateItems())
                    }
                }
            }
        }
    }

    fun tryToSave(exercise_id: String) {
        val currentState = _screenState.value

        if(currentState is NewThoughtDiaryScreenState.Content){
            val updatesFields = currentState.data.fields.map { delegateItem ->
                when (delegateItem) {
                    is InputExerciseDelegateItem -> {
                        val updatedContent = if (resultExercise.find { it.fieldId == delegateItem.content().id }?.value == "") {
                            delegateItem.content().copy(isNotCorrect = true)
                        } else {
                            delegateItem.content().copy(isNotCorrect = false)
                        }
                        val updateDelegateItem = InputExerciseDelegateItem(
                            updatedContent
                        )

                        updateDelegateItem
                    }
                    else -> delegateItem
                }
            }

            val hasErrors = updatesFields.any {
                it is InputExerciseDelegateItem && it.content().isNotCorrect
            }
            if (hasErrors){
                _screenState.value = currentState.copy(data = currentState.data.copy(fields = updatesFields))
            }else{
                //Save
            }
        }

    }

    private fun saveTextInput(res: ExerciseResultEntity) {
        val searchItem = resultExercise.find { it.fieldId == res.fieldId }
        searchItem!!.value = res.value

        val currentState = _screenState.value
        if(currentState is NewThoughtDiaryScreenState.Content){
            val updatesFields = currentState.data.fields.map { delegateItem ->
                when (delegateItem) {
                    is InputExerciseDelegateItem -> {
                        if (delegateItem.content().id == res.fieldId){
                            delegateItem.content().text = res.value
                        }
                        else if (delegateItem.content().text.isNotEmpty() && delegateItem.content().isNotCorrect){
                            delegateItem.content().isNotCorrect = false
                        }
                        else{
                            delegateItem.content()
                        }
                        delegateItem
                    }
                    else -> delegateItem
                }
            }
            _screenState.value = currentState.copy(data = currentState.data.copy(fields = updatesFields))
        }
    }

    private fun ExerciseDetailEntity.toDelegateItems(): ExerciseDetailWithDelegateItem {
        return ExerciseDetailWithDelegateItem(id = id, title = title, description = description,
            fields = fields.map { field ->
                when (field.type) {
                    TypeOfExercise.TextInput -> InputExerciseDelegateItem(
                        InputItemExerciseEntity(
                            id = field.id,
                            titleId = field.title,
                            helperId = field.description,
                            hintId = field.title,
                            saveFunction = { res -> saveTextInput(res) }
                        )
                    )

                    TypeOfExercise.NumberInput -> IntDelegateItem(
                        InputIntExerciseEntity(
                            id = field.id,
                            title = field.title,
                            value = "50",
                            saveResult = { res -> saveTextInput(res) }
                        )
                    )
                }
            }
        )
    }

    class Factory @Inject constructor(
        private val saveThoughtDiaryUseCase: SaveThoughtDiaryUseCase,
        private val getExerciseDetailUseCase: GetExerciseDetailUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return NewThoughtDiaryViewModel(saveThoughtDiaryUseCase, getExerciseDetailUseCase) as T
        }
    }
}
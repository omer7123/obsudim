package com.example.mypsychologist.presentation.exercises.definitionProblemGroupExerciseFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.exerciseEntity.DefinitionProblemGroupExerciseEntity
import com.example.mypsychologist.domain.useCase.exerciseUseCases.definitionProblemGroupExercise.SaveDefinitionProblemGroupResultUseCase
import com.example.mypsychologist.presentation.exercises.exercisesFragment.SaveStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefinitionProblemGroupExerciseViewModel @Inject constructor(
    private val saveDefinitionProblemGroupResultUseCase: SaveDefinitionProblemGroupResultUseCase
): ViewModel() {

    private var _screenState: MutableStateFlow<DefinitionProblemGroupExerciseContent> = MutableStateFlow(DefinitionProblemGroupExerciseContent())
    val screenState: StateFlow<DefinitionProblemGroupExerciseContent> = _screenState

    private var _statusSave: MutableStateFlow<SaveStatus> = MutableStateFlow(SaveStatus.Init)
    val statusSave: StateFlow<SaveStatus> = _statusSave

    fun saveResult(){
        val currentState = screenState.value
        _screenState.value = currentState.copy(
            scopeFieldErrorId = if (currentState.scopeField.isEmpty()) R.string.field_empty_placeholder else null,
            emotionFieldErrorId = if (currentState.emotionField.isEmpty()) R.string.field_empty_placeholder else null,
            targetFieldErrorId = if (currentState.targetField.isEmpty()) R.string.field_empty_placeholder else null
        )

        if (currentState.scopeField.isNotEmpty() && currentState.emotionField.isNotEmpty() && currentState.targetField.isNotEmpty()){
            val dataOfSave = DefinitionProblemGroupExerciseEntity(currentState.scopeField, currentState.emotionField, currentState.targetField)
            viewModelScope.launch {
                saveDefinitionProblemGroupResultUseCase(dataOfSave).collect {resource->
                    when(resource){
                        is Resource.Error -> _statusSave.value = SaveStatus.Error(resource.msg.toString())
                        Resource.Loading -> _screenState.update {
                            it.copy(loading = true)
                         }
                        is Resource.Success -> _statusSave.value = SaveStatus.Success
                    }
                }
            }
        }
    }

    fun changeScopeField(scope: String){
        _screenState.value = _screenState.value.copy(scopeField = scope, scopeFieldErrorId = null)
    }

    fun changeEmotionField(emotion: String){
        _screenState.value = _screenState.value.copy(emotionField = emotion, emotionFieldErrorId = null)
    }

    fun changeTargetField(target: String){
        _screenState.value = _screenState.value.copy(targetField = target, targetFieldErrorId = null)
    }
}
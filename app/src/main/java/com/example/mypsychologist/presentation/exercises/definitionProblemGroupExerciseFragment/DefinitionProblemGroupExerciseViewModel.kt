package com.example.mypsychologist.presentation.exercises.definitionProblemGroupExerciseFragment

import androidx.lifecycle.ViewModel
import com.example.mypsychologist.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DefinitionProblemGroupExerciseViewModel @Inject constructor(

): ViewModel() {

    private var _screenState: MutableStateFlow<DefinitionProblemGroupExerciseContent> = MutableStateFlow(DefinitionProblemGroupExerciseContent())
    val screenState: StateFlow<DefinitionProblemGroupExerciseContent> = _screenState

    fun saveResult(){
        val currentState = screenState.value
        _screenState.value = currentState.copy(
            scopeFieldErrorId = if (currentState.scopeField.isEmpty()) R.string.field_empty_placeholder else null,
            emotionFieldErrorId = if (currentState.emotionField.isEmpty()) R.string.field_empty_placeholder else null,
            targetFieldErrorId = if (currentState.targetField.isEmpty()) R.string.field_empty_placeholder else null
        )
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
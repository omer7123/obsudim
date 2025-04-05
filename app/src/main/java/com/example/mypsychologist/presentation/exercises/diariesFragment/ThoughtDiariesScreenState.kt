package com.example.mypsychologist.presentation.exercises.diariesFragment

import com.example.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity

sealed class ThoughtDiariesScreenState {
    data object Initial: ThoughtDiariesScreenState()
    data object Loading: ThoughtDiariesScreenState()
    data object Error: ThoughtDiariesScreenState()
    data class Content(val data: List<RecordExerciseEntity>): ThoughtDiariesScreenState()
}
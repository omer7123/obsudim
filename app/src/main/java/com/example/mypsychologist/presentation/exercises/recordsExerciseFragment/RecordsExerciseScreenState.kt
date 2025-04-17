package com.example.mypsychologist.presentation.exercises.recordsExerciseFragment

import com.example.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity

sealed class RecordsExerciseScreenState {
    data object Initial: RecordsExerciseScreenState()
    data object Loading: RecordsExerciseScreenState()
    data object Error: RecordsExerciseScreenState()
    data class Content(val data: List<RecordExerciseEntity>): RecordsExerciseScreenState()
}
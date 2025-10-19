package com.obsudim.mypsychologist.presentation.exercises.recordsExerciseFragment

import com.obsudim.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity

sealed class RecordsExerciseScreenState {
    data object Initial: RecordsExerciseScreenState()
    data object Loading: RecordsExerciseScreenState()
    data object Error: RecordsExerciseScreenState()
    data class Content(val data: List<RecordExerciseEntity>): RecordsExerciseScreenState()
}
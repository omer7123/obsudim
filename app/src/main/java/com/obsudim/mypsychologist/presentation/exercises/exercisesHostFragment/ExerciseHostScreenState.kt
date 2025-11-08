package com.obsudim.mypsychologist.presentation.exercises.exercisesHostFragment

import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseAllResultEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseInfoPreviewEntity

sealed interface ExerciseHostScreenState {
    data object Initial : ExerciseHostScreenState
    data class Content(
        val data: ExerciseInfoPreviewEntity,
        val history: List<ExerciseAllResultEntity>,
    ) : ExerciseHostScreenState

    data object Loading : ExerciseHostScreenState
    data object Error : ExerciseHostScreenState
}
package com.obsudim.mypsychologist.presentation.exercises.exerciseDemoResultFragment

import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultEntity

sealed interface ExerciseDemoResultScreenState {

    data class Content(
        val fields: List<ExerciseResultEntity>,
    ) : ExerciseDemoResultScreenState

    data object Initial : ExerciseDemoResultScreenState
    data object Error : ExerciseDemoResultScreenState
    data object Loading : ExerciseDemoResultScreenState
}

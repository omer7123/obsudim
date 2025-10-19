package com.obsudim.mypsychologist.presentation.exercises.exercisesFragment

import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExercisesStatusEntity

sealed interface ExercisesStatusScreenState {
    data object Initial:
        ExercisesStatusScreenState
    data object Loading:
        ExercisesStatusScreenState
    data object Error:
        ExercisesStatusScreenState
    data class Content(val data: List<ExercisesStatusEntity>):
        ExercisesStatusScreenState
}

sealed interface ExercisesScreenState {
    data object Init:
        ExercisesScreenState
    data class Error(val msg: String):
        ExercisesScreenState
    data object Loading:
        ExercisesScreenState
    data class Data(val data: List<ExerciseEntity>):
        ExercisesScreenState
}
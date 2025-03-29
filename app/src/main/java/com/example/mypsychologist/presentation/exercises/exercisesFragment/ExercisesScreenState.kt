package com.example.mypsychologist.presentation.exercises.exercisesFragment

import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity

sealed interface ExercisesScreenState {
    data object Initial: ExercisesScreenState
    data object Loading: ExercisesScreenState
    data object Error: ExercisesScreenState
    data class Content(val data: List<ExerciseEntity>): ExercisesScreenState
}
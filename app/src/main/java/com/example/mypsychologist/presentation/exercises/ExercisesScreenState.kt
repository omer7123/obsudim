package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity

sealed interface ExercisesScreenState {
    data object Init: ExercisesScreenState
    data class Error(val msg: String): ExercisesScreenState
    data object Loading: ExercisesScreenState
    data class Data(val data: List<ExerciseEntity>): ExercisesScreenState
}
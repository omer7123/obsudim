package com.example.mypsychologist.presentation.exercises.exercisesFragment

sealed interface SaveExerciseStatus {
    data class Error(val msg: String): SaveExerciseStatus
    data object Init: SaveExerciseStatus
    data object Success: SaveExerciseStatus
    data object Loading: SaveExerciseStatus
}
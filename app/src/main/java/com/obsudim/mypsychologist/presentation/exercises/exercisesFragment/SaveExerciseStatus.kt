package com.obsudim.mypsychologist.presentation.exercises.exercisesFragment

sealed interface SaveExerciseStatus {
    data class Error(val msg: String): SaveExerciseStatus
    data object Init: SaveExerciseStatus
    data object Success: SaveExerciseStatus
    data object Loading: SaveExerciseStatus
}

sealed interface SaveStatus{
    data class Error(val msg: String): SaveStatus
    data object Init: SaveStatus
    data object Success: SaveStatus
}
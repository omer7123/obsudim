package com.example.mypsychologist.presentation.exercises.definitionProblemGroupExerciseFragment

data class DefinitionProblemGroupExerciseContent(
    val scopeField: String = "",
    val scopeFieldErrorId: Int? = null,

    val emotionField: String = "",
    val emotionFieldErrorId: Int? = null,

    val targetField: String = "",
    val targetFieldErrorId: Int? = null,

    val loading: Boolean = false,
)
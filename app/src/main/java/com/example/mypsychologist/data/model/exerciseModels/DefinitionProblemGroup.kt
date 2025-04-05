package com.example.mypsychologist.data.model.exerciseModels

import kotlinx.serialization.Serializable

@Serializable
data class DefinitionProblemGroupExerciseModel(
    val sphere: String,
    val emotion: String,
    val target: String,
)

@Serializable
data class DefinitionProblemGroupHistoryModel(
    val id: String,
    val time: String,
    val sphere: String
)
package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExercisesModel(
    val id: String,
    val title: String,
    val description: String
)

@Serializable
data class ExerciseDetailModel(
    val id: String,
    val title: String,
    val description: String,
    val field: List<FieldExerciseModel>
)

@Serializable
data class FieldExerciseModel(
    val description: String,
    val title: String,
    val major: Boolean,
    @SerialName("exercise_structure_id")
    val exerciseStructureId: String,
    val type: Int,
    val id: String
)
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
@Serializable
data class ExerciseResultRequestModel(
    @SerialName("exercise_structure_id")
    val id: String,
    val result: List<ExerciseResultModel>
)
@Serializable
data class ExerciseResultModel(
    @SerialName("field_id")
    val fieldId: String,
    var value: String
)
@Serializable
data class SaveExerciseResultResponseModel(
    @SerialName("exercise_result_id")
    val id: String
)
@Serializable
data class DailyExerciseModel(
    val id: String,
    val type: Int,
    val title: String,
    @SerialName("short_description")
    val shortDescription: String,
    @SerialName("destination_id")
    val destinationId: String,
    @SerialName("is_complete")
    val isComplete: Boolean
)

@Serializable
data class DailyTaskMarkIdModel(
    @SerialName("daily_task_id")
    val id: String
)

@Serializable
data class StatusPostResponse(
    val status: String
)
package com.obsudim.mypsychologist.data.model.exerciseModels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseInfoPreview(
    val id: String,
    val title: String,
    val description: String,
    @SerialName("time_to_read")
    val timeToRead: Int,
    @SerialName("questions_count")
    val questionsCount: Int,
)

@Serializable
data class ExerciseMock(
    val exercises: List<ExercisesModel>
)
@Serializable
data class ExercisesModel(
    val id: String,
    val title: String,
    @SerialName("picture_link")
    val linkToPicture: String,
    val open: Boolean
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
    @SerialName("think_diary_id")
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

@Serializable
data class ExerciseResultFromAPIModel(
    @SerialName("completed_exercise_id")
    val completedExerciseId: String,
    val date: String
)

@Serializable
data class ExerciseDetailResultModel(
    val title: String,
    val date: String,
    val result: List<ExerciseResultModel>
)

@Serializable
data class ExercisesStatusModel(
    val title: String,
    @SerialName("is_closed")
    val isClosed: Boolean,
)

// ниже идет работа с упражнениями как отдельными таблицами

@Serializable
data class ExerciseSaveResponseModel(
    val message: String,
    @SerialName("exercise_id")
    val exerciseId: String
)
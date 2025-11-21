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
    @SerialName("pulled_fields")
    val pulledFields: List<String>,
    val id: String,
    val title: String,
    @SerialName("picture_link")
    val pictureLink: String,
    val description: String,
    @SerialName("time_to_read")
    val timeToRead: Int,
    @SerialName("questions_count")
    val questionsCount: Int,
    val open: Boolean,
    val pages: List<PagesExerciseModel>
)

@Serializable
data class PagesExerciseModel(
    @SerialName("page_number")
    val pageNumber: Int,
    val sections: List<SectionsExerciseModel>,
)

@Serializable
data class SectionsExerciseModel(
    val id: String,
    val title: String,
    val view: String,
    val type: String,
    val placeholder: String,
    val prompt: String,
    val variants: List<String>,
)

@Serializable
data class ExerciseResultRequestModel(
    @SerialName("exercise_structure_id")
    val id: String,
    @SerialName("filled_fields")
    val filledFields: List<TypeFieldModel>
)

@Serializable
sealed interface TypeFieldModel{
    @Serializable
    data class InputTextModel(
        @SerialName("field_id")
        val fieldId: String,
        val text: String
    ): TypeFieldModel

    @Serializable
    data class AddableListModel(
        @SerialName("field_id")
        val fieldId: String,
        val text: List<String>
    ): TypeFieldModel
}

@Serializable
data class SaveExerciseResultResponseModel(
    val id: String,
    val score: Int,
    @SerialName("picture_link")
    val pictureLink: String,
    val view: String,
    @SerialName("success_message")
    val successMessage: String,
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
data class ResultsExercise(
    val results: List<ExerciseResultFromAPIModel>
)

@Serializable
data class ExerciseResultFromAPIModel(
    val id: String,
    @SerialName("exercise_id")
    val exerciseId: String,
    val date: String,
    val preview: String,
)

@Serializable
data class ExerciseResultModel(
    val fieldId: String, var value: String
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
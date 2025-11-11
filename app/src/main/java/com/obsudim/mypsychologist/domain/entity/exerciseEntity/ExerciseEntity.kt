package com.obsudim.mypsychologist.domain.entity.exerciseEntity

data class ExerciseInfoPreviewEntity(
    val id: String,
    val title: String,
    val description: String,
    val timeToRead: Int,
    val questionsCount: Int,
)
data class ExerciseEntity(
    val id: String,
    val title: String,
    val linkToPicture: String,
    val open: Boolean
)

data class ExerciseDetailEntity(
    val pulledFields: List<String>,
    val id: String,
    val title: String,
    val pictureLink: String,
    val description: String,
    val timeToRead: Int,
    val questionsCount: Int,
    val open: Boolean,
    val pages: List<PagesExerciseEntity>
)

data class PagesExerciseEntity(
    val pageNumber: Int,
    val sections: List<SectionsExerciseEntity>,
)

data class SectionsExerciseEntity(
    val id: String,
    val title: String,
    val view: String,
    val type: TypeOfSection,
    val placeholder: String,
    val prompt: String,
    val variants: List<String>,
)

sealed interface TypeOfSection {
    data object TextInput : TypeOfSection
    data object AddableList : TypeOfSection
}

data class ExerciseAllResultEntity(
    val id: String,
    val exerciseId: String,
    val date: String,
    val preview: String,
)

data class ExerciseResultRequestEntity(
    val id: String, val result: List<ExerciseResultEntity>
)

data class ExerciseResultEntity(
    val fieldId: String, var value: String
)

data class DailyExerciseEntity(
    val id: String,
    val type: Int,
    val title: String,
    val shortDescription: String,
    val destinationId: String,
    val isComplete: Boolean
)

data class DailyTaskMarkIdEntity(
    val id: String
)

data class ExerciseResultFromAPIEntity(
    val completedExerciseId: String,
    val date: String
)

data class ExerciseDetailResultEntity(
    val title: String,
    val date: String,
    val result: List<ExerciseResultEntity>
)

data class RecordExerciseEntity(
    val id: String,
    val title: String,
    val date: String
)

data class ExercisesStatusEntity(
    val title: String,
    val isClosed: Boolean,
)
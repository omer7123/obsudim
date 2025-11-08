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
    val id: String,
    val title: String,
    val description: String,
    val fields: List<FieldExerciseEntity>
)

data class FieldExerciseEntity(
    val description: String,
    val title: String,
    val major: Boolean,
    val exerciseStructureId: String,
    val type: TypeOfExercise,
    val id: String
)

sealed interface TypeOfExercise {
    data object TextInput : TypeOfExercise
    data object NumberInput : TypeOfExercise
}

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
package com.example.mypsychologist.domain.entity.exerciseEntity

import com.example.mypsychologist.ui.DelegateItem

data class ExerciseEntity(
    val id: String,
    val title: String,
    val description: String
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
    data object TextInput: TypeOfExercise
    data object NumberInput: TypeOfExercise
}

data class ExerciseDetailWithDelegateItem(
    val id: String,
    val title: String,
    val description: String,
    val fields: List<DelegateItem>
)

data class ExerciseResultRequestEntity(
    val id: String,
    val result: List<ExerciseResultEntity>
)

data class ExerciseResultEntity(
    val fieldId: String,
    var value: String
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
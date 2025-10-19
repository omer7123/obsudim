package com.obsudim.mypsychologist.domain.entity

import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultEntity

data class InputItemEntity(
    val titleId: Int,
    val hintId: Int? = null,
    val helperId: Int? = null,
    val saveFunction: (String) -> Unit,
    val fieldName: String = "",
    val isNotCorrect: Boolean = false,
    val text: String = ""
)

data class InputItemExerciseEntity(
    val id: String,
    val titleId: String,
    val hintId: String? = null,
    val helperId: String? = null,
    val saveFunction: (ExerciseResultEntity) -> Unit,
    val fieldName: String = "",
    var isNotCorrect: Boolean = false,
    var text: String = ""
)

data class InputIntExerciseEntity(
    val id: String,
    val title: String,
    val value: String,
    val saveResult: (ExerciseResultEntity) -> Unit
)

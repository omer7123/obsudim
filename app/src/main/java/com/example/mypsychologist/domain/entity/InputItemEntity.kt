package com.example.mypsychologist.domain.entity

data class InputItemEntity(
    val titleId: Int,
    val hintId: Int? = null,
    val helperId: Int? = null,
    val saveFunction: (String) -> Unit,
    val fieldName: String = "",
    val isNotCorrect: Boolean = false,
    val text: String = ""
)


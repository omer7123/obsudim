package com.example.mypsychologist.domain.entity

data class ThoughtDiaryItemEntity(
    val titleId: Int,
    val hintId: Int,
    val helperId: Int,
    val saveFunction: (String) -> Unit,
    val fieldName: String = "",
    val isNotCorrect: Boolean = false,
    val text: String = ""
)


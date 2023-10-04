package com.example.mypsychologist.domain.entity

data class ThoughtDiaryItemEntity(
    val titleId: Int,
    val hintId: Int,
    val helperId: Int,
    val saveFunction: (String) -> Unit,
    val isNotCorrect: Boolean = false
)

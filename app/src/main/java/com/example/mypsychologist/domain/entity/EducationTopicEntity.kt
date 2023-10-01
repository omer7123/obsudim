package com.example.mypsychologist.domain.entity

data class EducationTopicEntity (
    val titleId: Int,
    val cardCount: Int,
    val tag: String,
    val currentCard: Int = 0
)

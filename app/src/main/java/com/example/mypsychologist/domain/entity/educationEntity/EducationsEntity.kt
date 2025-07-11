package com.example.mypsychologist.domain.entity.educationEntity

import java.io.Serializable

data class TopicEntity(
    val id: String,
    val theme: String,
    val score: Int,
    val maxScore: Int,
    val linkToPicture: String,
) : Serializable

data class EducationsEntity(
    val theme: String,
    val maxScore: Int,
    val materials: List<SubtopicEntity>,
)
data class SubtopicEntity(
    val subtitle: String,
    val cards: List<ItemMaterialEntity>,
)
data class ItemMaterialEntity(
    val id: String,
    val text: String,
    val linkToPicture: String,
)

data class EducationMaterialForSaveProgressEntity(
    val id: String,
)
package com.example.mypsychologist.domain.entity.educationEntity

import java.io.Serializable

data class ThemeEntity(
    val id: String,
    val theme: String,
    val score: Int,
    val maxScore: Int,
) : Serializable

data class ItemMaterialEntity(
    val id: String,
    val text: String
)

data class EducationMaterialForSaveProgressEntity(
    val id: String
)
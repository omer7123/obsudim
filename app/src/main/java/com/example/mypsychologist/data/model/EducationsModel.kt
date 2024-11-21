package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThemeModel(
    val id: String,
    val theme: String,
    val score: Int,
    @SerialName("max_score")
    val maxScore: Int,
)
@Serializable
data class EducationsModel(
    val theme: String,
    val score: Int,
    @SerialName("max_score")
    val maxScore: Int,
    val materials: List<ItemMaterialModel>
)
@Serializable
data class ItemMaterialModel(
    val id: String,
    val text: String
)

@Serializable
data class EducationMaterialForSaveProgressModel(
    @SerialName("education_material_id")
    val educationMaterialId: String
)

@Serializable
data class EduSaveResp(
    val status: String
)
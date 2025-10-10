package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThemeModel(
    val id: String,
    val theme: String,
    val link: String,
    val tags: List<String> = emptyList(),
    @SerialName("education_materials")
    val educationMaterials: List<EducationsModel>
)
@Serializable
data class EducationsModel(
    val id: String,
    val type: Int,
    val number: Int,
    val title: String,
    @SerialName("link_to_picture")
    val linkToPicture: String,
    val subtitle: String,
    val cards: List<ItemMaterialModel>,
)

@Serializable
data class ItemMaterialModel(
    val id: String,
    val text: String,
    val number: Int,
    @SerialName("link_to_picture")
    val linkToPicture: String,
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
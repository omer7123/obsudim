package com.obsudim.mypsychologist.domain.entity.educationEntity

data class TopicEntity(
    val id: String,
    val theme: String,
    val link: String,
    val recomendations: List<RecomendationEntity>,
    val educationMaterials: List<EducationsEntity>
)

data class RecomendationEntity (
    val id: String,
    val theme: String,
    val link: String,
    val tags: List<String>,
)
data class EducationsEntity(
    val id: String,
    val type: Int,
    val number: Int,
    val title: String,
    val linkToPicture: String,
    val subtitle: String,
    val cards: List<ItemMaterialEntity>
)
data class ItemMaterialEntity(
    val id: String,
    val text: String,
    val number: Int,
    val linkToPicture: String,
)

data class EducationMaterialForSaveProgressEntity(
    val id: String,
)
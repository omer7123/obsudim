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
    @SerialName("link_to_picture")
    val lincToPicture: String
)
@Serializable
data class EducationsModel(
    val theme: String,
    val id: String,
    @SerialName("max_score")
    val maxScore: Int,
    @SerialName("link_to_picture")
    val linkToPicture: String,
    @SerialName("related_topics")
    val relatedTopics: List<RelatedTopicModel>,
    val subtopics: List<SubtopicModel>
)
@Serializable
data class RelatedTopicModel(
    val id: String,
    val theme: String,
    @SerialName("link_to_picture")
    val linkToPicture: String,
    @SerialName("max_score")
    val maxScore: Int
)
@Serializable
data class SubtopicModel(
    val subtitle: String,
    val cards: List<ItemMaterialModel>
)
@Serializable
data class ItemMaterialModel(
    val id: String,
    val text: String,
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
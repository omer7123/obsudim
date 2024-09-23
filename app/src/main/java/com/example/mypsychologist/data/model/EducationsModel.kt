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
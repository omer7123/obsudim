package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FreeDiaryModel(
    @SerialName("free_diary_id")
    val freeDiaryId: String,
    val text: String
)

@Serializable
data class NewFreeDiaryModel (
    val text: String
)
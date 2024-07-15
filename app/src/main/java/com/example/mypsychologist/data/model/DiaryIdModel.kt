package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryIdModel(
    @SerialName("think_diary_id")
    val id: String
)

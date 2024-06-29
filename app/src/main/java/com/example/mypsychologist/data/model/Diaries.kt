package com.example.mypsychologist.data.model

import kotlinx.serialization.Serializable

data class FreeDiaryModel(
    val free_diary_id: String,
    val text: String
)

@Serializable
data class NewFreeDiaryModel (
    val text: String
)
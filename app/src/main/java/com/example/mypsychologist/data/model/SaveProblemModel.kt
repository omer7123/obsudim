package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaveProblemModel(
    val description: String,
    val goal: String
)

@Serializable
data class GetProblemModel(
    val id: String,
    @SerialName("user_id")
    val userId: String,
    val description: String,
    val goal: String
)

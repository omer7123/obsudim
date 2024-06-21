package com.example.mypsychologist.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProblemModel(
    val description: String,
    val goal: String
)

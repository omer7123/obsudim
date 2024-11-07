package com.example.mypsychologist.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ExercisesModel(
    val id: String,
    val title: String,
    val description: String
)
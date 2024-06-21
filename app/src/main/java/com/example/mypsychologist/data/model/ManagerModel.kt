package com.example.mypsychologist.data.model

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class ManagerModel(
    val username: String,
    val description: String,
    val city: String,
    val company: String,
    val gender: String,
    @Json(name = "birth_date")
    val birthDate: String
)
package com.example.mypsychologist.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Registration(
    val login: String,
    val password: String
)

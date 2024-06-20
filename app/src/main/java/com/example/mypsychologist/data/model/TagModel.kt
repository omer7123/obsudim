package com.example.mypsychologist.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TagModel (
    val id: Int,
    val text: String
)


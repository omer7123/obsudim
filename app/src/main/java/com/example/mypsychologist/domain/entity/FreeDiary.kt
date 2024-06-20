package com.example.mypsychologist.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class FreeDiary (
    val id: String,
    val text: String
)

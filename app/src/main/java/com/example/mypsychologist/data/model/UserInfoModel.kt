package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName

data class UserInfoModel(
    @SerialName("birth_date")
    val birthDate: String,
    val gender: String,
    val username: String,
    val request: List<Int>,
    val city: String,
    val description: String,
    val type: Int
)

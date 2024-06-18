package com.example.mypsychologist.data.model

import com.squareup.moshi.Json

data class UserInfoModel(
    @Json(name = "birth_date")
    val birthDate: String,
    val gender: String,
    val username: String,
    val request: List<Int>,
    val city: String,
    val description: String,
    val type: Int
)

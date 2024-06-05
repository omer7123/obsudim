package com.example.mypsychologist.domain.entity.authenticationEntity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class Auth(
    val email: String,
    val password: String,
)

data class Register(
    val auth: Auth,
    val checkPassword: String,
)

@Serializable
data class User(
    @SerializedName("user_id")
    val user_id: String,
    val email: String,
    val username: String,
    val token: String,
    val role: Int
)
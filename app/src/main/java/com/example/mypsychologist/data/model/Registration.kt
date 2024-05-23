package com.example.mypsychologist.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Registration(
    val login: String,
    val password: String
)

data class AuthModel(
    val email: String,
    val password: String,
)

data class RegisterModel(
    val auth: AuthModel,
    val checkPassword: String,
)

data class UserModel(
    val id: String,
    val email: String,
    val username: String,
    val photo: String?,
    val token: String,
    val role: String
)

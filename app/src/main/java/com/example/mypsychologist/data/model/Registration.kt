package com.example.mypsychologist.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AuthModel(
    val email: String,
    val password: String,
)

@Serializable
data class RegisterModel(
    val auth: AuthModel,
    val checkPassword: String,
)

@Serializable
data class UserModel(
    @SerializedName("user_id")
    val userId: String,
    val email: String,
    val username: String,
    val token: String,
    val role: Int
)

@Serializable
data class OldRegister(
    val email: String,
    val username: String,
    val password: String,
    val confirm_password: String
) //Для старого бэка, который сейчас

@Serializable
data class Token(
    val token: String
)
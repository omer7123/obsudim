package com.obsudim.mypsychologist.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AuthModel(
    val email: String,
    val password: String,
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
data class RegisterModel(
    val username: String,
    @SerialName("birth_date")
    val birthDate: String,
    val gender: String,
    val city: String,
    val email: String,
    @SerialName("phone_number")
    val phoneNumber: String,
    val password: String,
    @SerialName("confirm_password")
    val confirmPassword: String
)

@Serializable
data class Token(
    val token: String
)
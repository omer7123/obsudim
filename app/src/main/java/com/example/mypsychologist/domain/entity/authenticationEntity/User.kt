package com.example.mypsychologist.domain.entity.authenticationEntity

import com.example.mypsychologist.presentation.authentication.registrationFragment.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Auth(
    val email: String,
    val password: String,
)

data class Register(
    val auth: Auth,
    val checkPassword: String,
)
data class RegisterEntity(
    val username: String,
    val birthDate: String,
    val gender: Gender,
    val city: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val confirmPassword: String
)

@Serializable
data class User(
    val user_id: String,
    val email: String,
    val username: String,
    val token: String,
    val role: Int
)

@Serializable
data class Tokens(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
)

@Serializable
data class RefreshToken(
    @SerialName("refresh_token")
    val refreshToken: String,
)

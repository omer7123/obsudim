package com.example.mypsychologist.domain.entity.authenticationEntity

import com.example.mypsychologist.presentation.authentication.registrationFragment.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Auth(
    val email: String,
    val password: String,
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
    @SerialName("access_token")
    val accessToken: String,
)

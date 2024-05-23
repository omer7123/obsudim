package com.example.mypsychologist.domain.entity.authenticationEntity

data class Auth(
    val email: String,
    val password: String,
)

data class Register(
    val auth: Auth,
    val checkPassword: String,
)

data class User(
    val id: String,
    val email: String,
    val username: String,
    val photo: String?,
    val token: String,
    val role: String
)
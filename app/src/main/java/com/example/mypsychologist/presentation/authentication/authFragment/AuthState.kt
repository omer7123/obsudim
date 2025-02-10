package com.example.mypsychologist.presentation.authentication.authFragment

data class AuthContent (
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

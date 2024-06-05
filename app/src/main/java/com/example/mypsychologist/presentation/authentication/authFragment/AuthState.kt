package com.example.mypsychologist.presentation.authentication.authFragment

sealed class AuthState {

    data object Initial: AuthState()
    data object Loading: AuthState()
    data object Success: AuthState()
    data class Content(val email: Boolean, val password: Boolean): AuthState()
    data class Error(val msg: String): AuthState()
}

package com.example.mypsychologist.presentation.authentication.registrationFragment

sealed class RegisterState {

    data object Initial: RegisterState()
    data object Loading: RegisterState()
    data object Success: RegisterState()
    data class Content(val email: Boolean, val password: Boolean, val confirmPassword: Boolean): RegisterState()
    data class Error(val msg: String): RegisterState()
}
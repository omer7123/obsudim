package com.example.mypsychologist.presentation.authentication

sealed class RegisterState {

    data object Initial: RegisterState()
    data object Loading: RegisterState()
    data object Success: RegisterState()
    data object Error: RegisterState()
}
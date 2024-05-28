package com.example.mypsychologist.presentation.authentication

import com.example.mypsychologist.data.model.UserModel

sealed class RegisterState {

    data object Initial: RegisterState()
    data object Loading: RegisterState()
    data object Success: RegisterState()
    data class Error(val msg: String): RegisterState()
}
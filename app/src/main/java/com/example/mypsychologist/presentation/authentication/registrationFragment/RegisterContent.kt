package com.example.mypsychologist.presentation.authentication.registrationFragment

import androidx.compose.runtime.Immutable

@Immutable
data class RegisterContent(
    val step: StepScreen,
    val name: String = "",
    val birthday: String = "",
    val gender: Gender = Gender.INITIAL,
    val city: String = "",

    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",

)
sealed interface RegisterStatus{
    data object Initial: RegisterStatus
    data object Loading: RegisterStatus
    data class Error(val msg: String): RegisterStatus
    data object Success: RegisterStatus
}

sealed class StepScreen{
    data object PersonalScreen: StepScreen()
    data object RegistrationScreen: StepScreen()
}

enum class Gender{
    MALE,
    FEMALE,
    INITIAL
}
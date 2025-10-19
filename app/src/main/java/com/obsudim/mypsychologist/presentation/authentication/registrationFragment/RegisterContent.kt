package com.obsudim.mypsychologist.presentation.authentication.registrationFragment

import androidx.compose.runtime.Immutable

@Immutable
data class RegisterContent(
    val step: StepScreen,
    val name: String = "",
    val nameError: Int? = null,
    val birthday: String = "",
    val birthdayError: Int? = null,
    val gender: Gender = Gender.INITIAL,
    val city: String = "",
    val cityError: Int? = null,

    val email: String = "",
    val emailError: Int? = null,
    val phoneNumber: String = "",
    val phoneNumberError: Int? = null,
    val password: String = "",
    val passwordError: Int? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: Int? = null,

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
package com.example.mypsychologist.presentation.authentication.registrationFragment

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.authenticationEntity.RegisterEntity
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.useCase.authenticationUseCases.RegisterUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.SaveTokenUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.SaveUserIdUseCase
import com.example.mypsychologist.extensions.convertDateToBackendFormatString
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase
) : ViewModel() {

    private val _stateScreen: MutableStateFlow<RegisterContent> =
        MutableStateFlow(RegisterContent(step = StepScreen.PersonalScreen))
    val stateScreen: StateFlow<RegisterContent> = _stateScreen

    private val _statusRegistration: MutableStateFlow<RegisterStatus> =
        MutableStateFlow(RegisterStatus.Initial)
    val statusRegistration: StateFlow<RegisterStatus> = _statusRegistration

    private val handler = CoroutineExceptionHandler { _, error ->
        _statusRegistration.value = RegisterStatus.Error(error.message.toString())
    }

    private suspend fun saveToken(result: Resource.Success<User>) {
        saveTokenUseCase(result.data.token)
        saveUserIdUseCase(result.data.user_id)
        _statusRegistration.value = RegisterStatus.Success
    }

    fun onNextClick() {
        val name = stateScreen.value.name
        val city = stateScreen.value.city
        val birthday = stateScreen.value.birthday

        if (name.isEmpty()) _stateScreen.value =
            stateScreen.value.copy(nameError = R.string.name_empty_placeholder)
        if (city.isEmpty()) _stateScreen.value =
            stateScreen.value.copy(cityError = R.string.city_empty_placeholder)
        if (birthday.isEmpty()) _stateScreen.value =
            stateScreen.value.copy(birthdayError = R.string.birthday_empty_placeholder)

        if (stateScreen.value.name.isNotEmpty() && stateScreen.value.city.isNotEmpty() && stateScreen.value.gender != Gender.INITIAL && stateScreen.value.birthday.isNotEmpty()) {
            _stateScreen.value = stateScreen.value.copy(step = StepScreen.RegistrationScreen)
        }
    }

    fun prevStep() {
        _stateScreen.value = stateScreen.value.copy(step = StepScreen.PersonalScreen)
    }

    @SuppressLint("NewApi")
    fun register() {
        validationData()

        val currentStateAfterValidation = stateScreen.value
        val email = currentStateAfterValidation.email
        val phoneNumber = currentStateAfterValidation.phoneNumber
        val password = currentStateAfterValidation.password
        val confirmPassword = currentStateAfterValidation.confirmPassword

        if (currentStateAfterValidation.emailError == null &&
            currentStateAfterValidation.phoneNumberError == null &&
            currentStateAfterValidation.passwordError == null &&
            currentStateAfterValidation.confirmPasswordError == null
        ) {

            val registerEntity = RegisterEntity(
                username = currentStateAfterValidation.name,
                birthDate = convertDateToBackendFormatString(currentStateAfterValidation.birthday),
                gender = currentStateAfterValidation.gender,
                city = currentStateAfterValidation.city,
                email = email,
                phoneNumber = phoneNumber,
                password = password,
                confirmPassword = confirmPassword
            )

            viewModelScope.launch(handler) {
                _statusRegistration.value = RegisterStatus.Loading
                when (val result = registerUseCase(registerEntity)) {
                    is Resource.Error -> _statusRegistration.value =
                        RegisterStatus.Error(result.msg.toString())

                    Resource.Loading -> _statusRegistration.value = RegisterStatus.Loading

                    is Resource.Success -> {
                        saveToken(result)
                    }
                }
            }
        }
    }

    private fun validationData() {
        val currentState = stateScreen.value
        val email = currentState.email
        val phoneNumber = currentState.phoneNumber
        val password = currentState.password
        val confirmPassword = currentState.confirmPassword

        if (email.isEmpty()) {
            _stateScreen.value =
                stateScreen.value.copy(emailError = R.string.email_empty_placeholder)
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _stateScreen.value = stateScreen.value.copy(emailError = R.string.email_not_valid_placeholder)
        }
        if (phoneNumber.isEmpty()){
            _stateScreen.value =
                stateScreen.value.copy(phoneNumberError = R.string.phone_empty_placeholder)
        }else if(phoneNumber.length<10){
            _stateScreen.value = stateScreen.value.copy(phoneNumberError = R.string.phone_not_valid_placeholder)
        }
        if (password.isEmpty()) _stateScreen.value =
            stateScreen.value.copy(passwordError = R.string.password_empty_placeholder)
        if (confirmPassword.isEmpty()) {
            _stateScreen.value =
                stateScreen.value.copy(confirmPasswordError = R.string.password_confirm_empty_placeholder)
        }else if(confirmPassword != password){
            _stateScreen.value = currentState.copy(confirmPasswordError = R.string.password_confirm_not_equals_placeholder)
        }
    }

    fun changeName(name: String) {
        _stateScreen.value = stateScreen.value.copy(name = name, nameError = null)
    }

    fun changeCity(city: String) {
        _stateScreen.value = stateScreen.value.copy(city = city, cityError = null)
    }

    fun changeGender(gender: Gender) {
        _stateScreen.value = stateScreen.value.copy(gender = gender)
    }

    fun changeBirthday(date: String) {
        _stateScreen.value = stateScreen.value.copy(birthday = date, birthdayError = null)
    }

    fun changeEmail(email: String) {
        _stateScreen.value = stateScreen.value.copy(email = email, emailError = null)
    }

    fun changePhone(phone: String) {
        _stateScreen.value = stateScreen.value.copy(phoneNumber = phone, phoneNumberError = null)
    }

    fun changePassword(password: String) {
        _stateScreen.value = _stateScreen.value.copy(password = password, passwordError = null)
    }

    fun changeConfirmPassword(password: String) {
        _stateScreen.value =
            _stateScreen.value.copy(confirmPassword = password, confirmPasswordError = null)
    }
}
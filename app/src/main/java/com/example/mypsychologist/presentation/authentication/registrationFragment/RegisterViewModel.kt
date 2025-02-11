package com.example.mypsychologist.presentation.authentication.registrationFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.useCase.authenticationUseCases.RegisterUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.SaveTokenUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.SaveUserIdUseCase
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

    fun register(register: OldRegister) {
        if (register.email.isNotEmpty() && register.password.isNotEmpty() && register.confirm_password.isNotEmpty() && register.password == register.confirm_password) {
            viewModelScope.launch(handler) {
                _statusRegistration.value = RegisterStatus.Loading
                when (val result = registerUseCase(register)) {
                    is Resource.Error -> _statusRegistration.value =
                        RegisterStatus.Error(result.msg.toString())

                    Resource.Loading -> _statusRegistration.value = RegisterStatus.Loading
                    is Resource.Success -> {
                        saveToken(result)
                    }
                }
            }
        } else {
//            _stateScreen.value = RegisterStatus.Content(
//                email = register.email.isEmpty(),
//                password = register.password.isEmpty(),
//                confirmPassword = register.confirm_password.isEmpty(),
//            )
        }
    }

    private suspend fun saveToken(result: Resource.Success<User>) {
        saveTokenUseCase(result.data.token)
        saveUserIdUseCase(result.data.user_id)
        _statusRegistration.value = RegisterStatus.Success
    }

    fun changeName(name: String){
        _stateScreen.value = stateScreen.value.copy(name = name)
    }

    fun changeCity(city: String){
        _stateScreen.value = stateScreen.value.copy(city = city)
    }

    fun changeGender(gender: Gender){
        _stateScreen.value = stateScreen.value.copy(gender = gender)
    }
}
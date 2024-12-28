package com.example.mypsychologist.presentation.authentication.registrationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.useCase.authenticationUseCases.AuthByTokenUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.GetTokenUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.RegisterUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.SaveTokenUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.SaveUserIdUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val authByTokenUseCase: AuthByTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase
) : ViewModel() {

    private val _stateScreen: MutableLiveData<RegisterState> =
        MutableLiveData(RegisterState.Initial)

    val stateScreen: LiveData<RegisterState> = _stateScreen

    private val handler = CoroutineExceptionHandler { _, error ->
        _stateScreen.value = RegisterState.Error(error.message.toString())
    }


    private fun saveToken(result: Resource.Success<User>) {
        viewModelScope.launch(handler) {
            _stateScreen.value = RegisterState.Loading
            saveTokenUseCase(result.data.token)
            saveUserIdUseCase(result.data.user_id)
            _stateScreen.value = RegisterState.Success
        }
    }

    fun register(register: OldRegister) {
        if (register.email.isNotEmpty() && register.password.isNotEmpty() && register.confirm_password.isNotEmpty() && register.password == register.confirm_password) {
            viewModelScope.launch(handler) {
                _stateScreen.value = RegisterState.Loading
                when (val result = registerUseCase(register)) {
                    is Resource.Error -> _stateScreen.value =
                        RegisterState.Error(result.msg.toString())

                    Resource.Loading -> _stateScreen.value = RegisterState.Loading
                    is Resource.Success -> {
                        saveToken(result)
                    }
                }
            }
        } else {
            _stateScreen.value = RegisterState.Content(
                email = register.email.isEmpty(),
                password = register.password.isEmpty(),
                confirmPassword = register.confirm_password.isEmpty(),
            )
        }
    }

    fun authByToken() {
        viewModelScope.launch(handler) {
            _stateScreen.value = RegisterState.Loading
            val token = getTokenUseCase()
            if (token == "")
                _stateScreen.value = RegisterState.Initial
            else {
                when (val result = authByTokenUseCase(Token(token))) {
                    is Resource.Error -> _stateScreen.value =
                        RegisterState.Error(result.msg.toString())

                    Resource.Loading -> _stateScreen.value = RegisterState.Loading
                    is Resource.Success -> {
                        saveTokenUseCase(result.data.token)
                        _stateScreen.value = RegisterState.SuccessAuth
                    }
                }
            }
        }
    }
}
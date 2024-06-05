package com.example.mypsychologist.presentation.authentication.registrationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases.RegisterUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases.SaveTokenUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    private val _stateScreen: MutableLiveData<RegisterState> =
        MutableLiveData(RegisterState.Initial)

    val stateScreen: LiveData<RegisterState> = _stateScreen

    private val handler = CoroutineExceptionHandler { _, error ->
        when (error) {
            is IllegalArgumentException -> {
                _stateScreen.value =
                    RegisterState.Error(error.toString())
            }

            is NullPointerException -> {
                _stateScreen.value =
                    RegisterState.Error(error.toString())
            }

            else -> {
                throw error
            }
        }
    }


    private fun saveToken(result: Resource.Success<User>) {
        viewModelScope.launch(handler) {
            _stateScreen.value = RegisterState.Loading
            saveTokenUseCase(result.data.token)
            _stateScreen.value = RegisterState.Success
        }
    }

    fun register(register: OldRegister) {
        if (register.email.isNotEmpty() && register.password.isNotEmpty() && register.confirm_password.isNotEmpty() && register.password == register.confirm_password) {
            viewModelScope.launch {
                when (val result = registerUseCase.registerOld(register)) {
                    is Resource.Error -> _stateScreen.value = RegisterState.Error(result.msg.toString())
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

}
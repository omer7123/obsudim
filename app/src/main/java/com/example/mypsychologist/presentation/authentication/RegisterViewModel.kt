package com.example.mypsychologist.presentation.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.authenticationEntity.Register
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
    val stateScreen: LiveData<RegisterState> get() = _stateScreen

    private val handler = CoroutineExceptionHandler { _, error ->
        when (error) {
            is IllegalArgumentException -> {
                _stateScreen.value =
                    RegisterState.Error
            }

            is NullPointerException -> {
                _stateScreen.value =
                    RegisterState.Error
            }
            else->{
                throw error
            }
        }
    }

    fun register(register: Register) {
        viewModelScope.launch {
            when (val result = registerUseCase(register)) {
                is Resource.Error -> _stateScreen.value = RegisterState.Error
                Resource.Loading -> _stateScreen.value = RegisterState.Loading
                is Resource.Success -> saveToken(result)
            }
        }
    }

    private fun saveToken(result: Resource.Success<User>) {
        viewModelScope.launch(handler) {
            saveTokenUseCase(result.data.token)
        }
    }
}
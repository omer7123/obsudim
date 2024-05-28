package com.example.mypsychologist.presentation.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.domain.entity.authenticationEntity.Register
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.useCase.GetMBITestUseCase
import com.example.mypsychologist.domain.useCase.MBIConclusionUseCase
import com.example.mypsychologist.domain.useCase.SaveMBIResultUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases.RegisterUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases.SaveTokenUseCase
import com.example.mypsychologist.presentation.diagnostics.MBITestViewModel
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

    fun register(register: Register) {
        viewModelScope.launch {
            when (val result = registerUseCase.register(register)) {
                is Resource.Error -> _stateScreen.value = RegisterState.Error(result.msg.toString())
                Resource.Loading -> _stateScreen.value = RegisterState.Loading
                is Resource.Success -> {
                    saveToken(result)
                }
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

    fun registerOld(register: OldRegister){
        viewModelScope.launch {
            when(val res = registerUseCase.registerOld(register)){
                is Resource.Error -> _stateScreen.value = RegisterState.Error(res.msg.toString())
                Resource.Loading -> _stateScreen.value = RegisterState.Loading
                is Resource.Success -> _stateScreen.value = RegisterState.Success
            }
        }
    }

    class Factory @Inject constructor(
        private val registerUseCase: RegisterUseCase,
        private val saveTokenUseCase: SaveTokenUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(
                registerUseCase, saveTokenUseCase
            ) as T
        }
    }
}
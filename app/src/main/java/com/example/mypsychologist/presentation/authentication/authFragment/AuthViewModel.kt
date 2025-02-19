package com.example.mypsychologist.presentation.authentication.authFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.useCase.authenticationUseCases.AuthByTokenUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.AuthWithDataUserUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.GetTokenUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.SaveTokenUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.SaveUserIdUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authWithDataUserUseCase: AuthWithDataUserUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase,
    private val authByTokenUseCase: AuthByTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase,
) : ViewModel() {

    private val _stateScreen: MutableStateFlow<AuthContent> = MutableStateFlow(AuthContent())
    val stateScreen: StateFlow<AuthContent> = _stateScreen

    private val _authByTokenStatus: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Initial)
    val authByTokenStatus: StateFlow<AuthState> = _authByTokenStatus


    private val handler = CoroutineExceptionHandler { _, error ->
        _stateScreen.value = _stateScreen.value.copy(error = error.toString())
    }

    fun auth() {
        val email = _stateScreen.value.email
        val password = _stateScreen.value.password
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch(handler) {
                _stateScreen.value = _stateScreen.value.copy(loading = true, error = null)
                when (val result = authWithDataUserUseCase(AuthModel(email, password))) {
                    is Resource.Error -> _stateScreen.value = _stateScreen.value.copy(error = result.msg.toString(), loading = false)
                    Resource.Loading -> _stateScreen.value = _stateScreen.value.copy(error = null, loading = true)
                    is Resource.Success -> saveToken(result.data)
                }
            }
        }
    }

    fun authByToken() {
        viewModelScope.launch(handler) {
            _authByTokenStatus.value = AuthState.Loading
            val token = getTokenUseCase()
            if (token == "")
                _authByTokenStatus.value = AuthState.Error
            else {
                when (val result = authByTokenUseCase(Token(token))) {
                    is Resource.Error -> _authByTokenStatus.value = AuthState.Error

                    Resource.Loading -> _authByTokenStatus.value = AuthState.Loading
                    is Resource.Success -> {
                        saveTokenUseCase(result.data.token)
                        _authByTokenStatus.value = AuthState.Success
                    }
                }
            }
        }
    }
    private suspend fun saveToken(result: User) {
        _stateScreen.value = _stateScreen.value.copy(loading = true)
        saveTokenUseCase(result.token)
        saveUserIdUseCase(result.user_id)
        _authByTokenStatus.value = AuthState.Success
    }

    fun emailChange(email: String){
        _stateScreen.value = _stateScreen.value.copy(email = email)
    }

    fun passwordChange(password: String){
        _stateScreen.value = _stateScreen.value.copy(password = password)
    }

    fun removeError() {
        _stateScreen.value = _stateScreen.value.copy(error = null)
    }
}
package com.obsudim.mypsychologist.presentation.authentication.authFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.AuthModel
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.Tokens
import com.obsudim.mypsychologist.domain.useCase.authenticationUseCases.AuthByTokenUseCase
import com.obsudim.mypsychologist.domain.useCase.authenticationUseCases.AuthWithDataUserUseCase
import com.obsudim.mypsychologist.domain.useCase.authenticationUseCases.GetTokenUseCase
import com.obsudim.mypsychologist.domain.useCase.authenticationUseCases.SaveRefreshTokenUseCase
import com.obsudim.mypsychologist.domain.useCase.authenticationUseCases.SaveTokenUseCase
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetAllDailyExercisesUseCase
import com.obsudim.mypsychologist.domain.useCase.profile.GetInfoMeUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authWithDataUserUseCase: AuthWithDataUserUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val authByTokenUseCase: AuthByTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val getAllDailyExercisesUseCase: GetAllDailyExercisesUseCase,
    private val getAuthMeUseCase: GetInfoMeUseCase,
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
            Log.e("tok", token.toString())
            if (token == "")
                _authByTokenStatus.value = AuthState.Error
            else {
                getAuthMeUseCase()
                    .collect { result ->
                        Log.e("SAuth", "Auth states: $result")
                        Log.e("SAuth", "Auth states Code: ${result.toString()}")
                        _authByTokenStatus.value = when (result) {
                            is Resource.Error -> AuthState.Error
                            Resource.Loading -> AuthState.Loading
                            is Resource.Success -> {
                                Log.e("SAuth", "Auth success")
                                AuthState.Success
                            }
                        }
                    }
            }
        }
    }
    private suspend fun saveToken(result: Tokens) {
        _stateScreen.value = _stateScreen.value.copy(loading = true)
        saveTokenUseCase(result.accessToken)
        saveRefreshTokenUseCase(result.refreshToken)
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
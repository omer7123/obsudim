package com.obsudim.mypsychologist.presentation.authentication.authFragment

data class AuthContent (
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
)

sealed interface AuthState{
    data object Success: AuthState
    data object Loading: AuthState
    data object Error : AuthState
    data object Initial: AuthState
}

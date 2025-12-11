package com.obsudim.mypsychologist.presentation.authentication.resetPasswordFragment

sealed interface ResetPasswordScreenState {
    data object Initial : ResetPasswordScreenState
    data class Content(
        val email: String = "",
        val isLoading: Boolean = false,
    ) : ResetPasswordScreenState

    data object Error : ResetPasswordScreenState
    data object SuccessRequest : ResetPasswordScreenState
}
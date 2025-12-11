package com.obsudim.mypsychologist.presentation.authentication.resetPasswordFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResetPasswordViewModel @Inject constructor(

) : ViewModel() {
    private val _screenState: MutableStateFlow<ResetPasswordScreenState> =
        MutableStateFlow(ResetPasswordScreenState.Content())

    val screenState: StateFlow<ResetPasswordScreenState> = _screenState.asStateFlow()

    fun sendRequestToResetPassword(){
        viewModelScope.launch {

        }
    }
}
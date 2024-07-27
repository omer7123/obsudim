package com.example.mypsychologist.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.DeleteAccountUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases.DeleteTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase
) : ViewModel() {

    private val _goToAuthorization: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val goToAuthorization: StateFlow<Boolean>
        get() = _goToAuthorization.asStateFlow()

    fun signOut() {

        viewModelScope.launch {
            deleteTokenUseCase()
            _goToAuthorization.value = true
        }
//        FirebaseAuth.getInstance().signOut()

    }

    fun deleteAccount() {
        viewModelScope.launch {
            _goToAuthorization.value = deleteAccountUseCase()
        }
    }

    class Factory @Inject constructor(
        private val deleteAccountUseCase: DeleteAccountUseCase,
        private val deleteTokenUseCase: DeleteTokenUseCase
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(deleteAccountUseCase, deleteTokenUseCase) as T
        }
    }
}
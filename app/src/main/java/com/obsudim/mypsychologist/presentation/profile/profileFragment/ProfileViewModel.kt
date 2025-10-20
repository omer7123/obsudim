package com.obsudim.mypsychologist.presentation.profile.profileFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.domain.useCase.authenticationUseCases.DeleteTokenUseCase
import com.obsudim.mypsychologist.domain.useCase.authenticationUseCases.DeleteUserIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel(
    private val deleteTokenUseCase: DeleteTokenUseCase,
    private val deleteUserIdUseCase: DeleteUserIdUseCase
) : ViewModel() {

    private val _goToAuthorization: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val goToAuthorization: StateFlow<Boolean>
        get() = _goToAuthorization.asStateFlow()

    fun signOut() {
        viewModelScope.launch {
            deleteTokenUseCase()
            deleteUserIdUseCase()
            _goToAuthorization.value = true
        }
    }

    class Factory @Inject constructor(
        private val deleteTokenUseCase: DeleteTokenUseCase,
        private val deleteUserIdUseCase: DeleteUserIdUseCase
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(
                deleteTokenUseCase,
                deleteUserIdUseCase
            ) as T
        }
    }
}
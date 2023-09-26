package com.example.mypsychologist.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.GetClientInfoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClientInfoViewModel @AssistedInject constructor(
    @Assisted private val clientId: String,
    private val getClientInfoUseCase: GetClientInfoUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<ClientInfoScreenState> =
        MutableStateFlow(ClientInfoScreenState.Init)
    val screenState: StateFlow<ClientInfoScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = ClientInfoScreenState.Loading
        viewModelScope.launch {
            _screenState.value = ClientInfoScreenState.Data(getClientInfoUseCase(clientId))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted id: String
        ): ClientInfoViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: Factory, id: String) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    assistedFactory.create(id) as T
            }
    }
}
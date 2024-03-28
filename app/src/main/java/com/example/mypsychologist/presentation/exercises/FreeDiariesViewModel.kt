package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.GetClientFreeDiariesUseCase
import com.example.mypsychologist.domain.useCase.GetFreeDiariesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FreeDiariesViewModel @AssistedInject constructor(
    private val getFreeDiariesUseCase: GetFreeDiariesUseCase,
    private val getClientFreeDiariesUseCase: GetClientFreeDiariesUseCase,
    @Assisted private val clientId: String
):ViewModel() {

    private val _screenState: MutableStateFlow<ThoughtDiariesScreenState> =
        MutableStateFlow(ThoughtDiariesScreenState.Init)

    val screenState: StateFlow<ThoughtDiariesScreenState>
        get() = _screenState.asStateFlow()

    fun loadDiaries() {
        _screenState.value = ThoughtDiariesScreenState.Loading

        viewModelScope.launch {
            _screenState.value =
                if (clientId == OWN)
                    ThoughtDiariesScreenState.Data(getFreeDiariesUseCase())
                else
                    ThoughtDiariesScreenState.Data(getClientFreeDiariesUseCase(clientId))
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted clientId: String
        ): FreeDiariesViewModel
    }
    companion object {
        fun provideFactory(
            assistedFactory: FreeDiariesViewModel.Factory,
            clientId: String
        ) = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(clientId) as T
        }

        const val OWN = "own"
    }
}
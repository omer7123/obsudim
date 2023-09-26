package com.example.mypsychologist.presentation.psychologist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.GetPsychologistUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PsychologistViewModel @AssistedInject constructor(
    private val getPsychologistUseCase: GetPsychologistUseCase,
    @Assisted private val id: String
) : ViewModel() {

    private val _screenState: MutableStateFlow<PsychologistScreenState> =
        MutableStateFlow(PsychologistScreenState.Init)
    val screenState: StateFlow<PsychologistScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = PsychologistScreenState.Loading

        viewModelScope.launch {
            _screenState.value = PsychologistScreenState.Data(getPsychologistUseCase(id))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted id: String
        ): PsychologistViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: Factory, id: String) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    assistedFactory.create(id) as T
            }
    }
}
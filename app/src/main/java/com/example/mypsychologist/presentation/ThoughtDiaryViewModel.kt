package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mypsychologist.domain.useCase.GetThoughtDiaryUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThoughtDiaryViewModel @AssistedInject constructor(
    private val getThoughtDiaryUseCase: GetThoughtDiaryUseCase,
    @Assisted private val id: Int
) : ViewModel() {

    private val _screenState: MutableStateFlow<ThoughtDiaryScreenState> =
        MutableStateFlow(ThoughtDiaryScreenState.Init)

    val screenState: StateFlow<ThoughtDiaryScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = ThoughtDiaryScreenState.Data(getThoughtDiaryUseCase(id))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted id: Int
        ) : ThoughtDiaryViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            id: Int
        ) = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(id) as T
        }
    }

}
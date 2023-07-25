package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.EditAlternativeThoughtUseCase
import com.example.mypsychologist.domain.useCase.EditAutoThoughtUseCase
import com.example.mypsychologist.domain.useCase.GetThoughtDiaryUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThoughtDiaryViewModel @AssistedInject constructor(
    private val getThoughtDiaryUseCase: GetThoughtDiaryUseCase,
    private val editAutoThoughtUseCase: EditAutoThoughtUseCase,
    private val editAlternativeThoughtUseCase: EditAlternativeThoughtUseCase,
    @Assisted private val id: String
) : ViewModel() {

    private val _screenState: MutableStateFlow<ThoughtDiaryScreenState> =
        MutableStateFlow(ThoughtDiaryScreenState.Init)
    val screenState: StateFlow<ThoughtDiaryScreenState>
        get() = _screenState.asStateFlow()

    init {
        loadDiary()
    }

    private fun loadDiary() {
        _screenState.value = ThoughtDiaryScreenState.Loading

        viewModelScope.launch {
            _screenState.value = ThoughtDiaryScreenState.Data(getThoughtDiaryUseCase(id))
        }
    }

    fun editAutoThought(newText: String) {
        viewModelScope.launch {
            _screenState.value =
                if (editAutoThoughtUseCase(id, newText))
                    ThoughtDiaryScreenState.EditingSuccess
                else
                    ThoughtDiaryScreenState.Error
        }
    }

    fun editAlternativeThought(newText: String) {
        viewModelScope.launch {
            _screenState.value =
                if (editAlternativeThoughtUseCase(id, newText))
                    ThoughtDiaryScreenState.EditingSuccess
                else
                    ThoughtDiaryScreenState.Error
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted id: String
        ): ThoughtDiaryViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            id: String
        ) = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(id) as T
        }
    }

}
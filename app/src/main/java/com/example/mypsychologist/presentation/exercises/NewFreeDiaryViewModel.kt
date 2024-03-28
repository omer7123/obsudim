package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mypsychologist.domain.entity.FreeDiaryEntity
import com.example.mypsychologist.domain.useCase.SaveFreeDiaryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NewFreeDiaryViewModel(private val saveThoughtDiaryUseCase: SaveFreeDiaryUseCase) :
    ViewModel() {

    private val _screenState: MutableStateFlow<NewFreeDiaryScreenState> =
        MutableStateFlow(NewFreeDiaryScreenState.Init)
    val screenState: StateFlow<NewFreeDiaryScreenState>
        get() = _screenState.asStateFlow()


    fun tryToSaveDiary(diary: FreeDiaryEntity) {
        _screenState.value =
            NewFreeDiaryScreenState.RequestResult(saveThoughtDiaryUseCase(diary))

    }

    class Factory @Inject constructor(private val saveFreeDiaryUseCase: SaveFreeDiaryUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return NewFreeDiaryViewModel(saveFreeDiaryUseCase) as T
        }
    }
}


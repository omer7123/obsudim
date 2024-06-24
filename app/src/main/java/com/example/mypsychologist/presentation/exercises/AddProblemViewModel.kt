package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.useCase.SaveProblemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddProblemViewModel(private val saveProblemUseCase: SaveProblemUseCase) : ViewModel() {

    private val _screenState: MutableStateFlow<NewProblemScreenState> =
        MutableStateFlow(NewProblemScreenState.Init)
    val screenState: StateFlow<NewProblemScreenState>
        get() = _screenState.asStateFlow()

    val moods = mutableListOf<String>()

    fun tryToSave(problem: String, goal: String) {
        viewModelScope.launch {
            _screenState.value =
                if (problem.isNotEmpty()) {
                    try {
                        NewProblemScreenState.Success(saveProblemUseCase(ProblemEntity(problem, goal = goal)))
                    } catch (t: Throwable) {
                        NewProblemScreenState.Error
                    }
                } else {
                    NewProblemScreenState.ValidationError(problem.isNotEmpty(), moods.isNotEmpty())
                }
        }
    }

    class Factory @Inject constructor(private val saveProblemUseCase: SaveProblemUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AddProblemViewModel(saveProblemUseCase) as T
        }
    }
}
package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.useCase.GetProblemsUseCase
import com.example.mypsychologist.ui.exercises.rebt.ProblemDelegateItem
import com.example.mypsychologist.ui.exercises.rebt.ProblemsDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProblemsViewModel(getProblemsUseCase: GetProblemsUseCase) : ViewModel() {

    private val _screenState: MutableStateFlow<ProblemsScreenState> =
        MutableStateFlow(ProblemsScreenState.Init)
    val screenState: StateFlow<ProblemsScreenState>
        get() = _screenState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = ProblemsScreenState.Data(getProblemsUseCase())
        }
    }

    fun add(problem: String, id: String) {
        val newMap = HashMap((screenState.value as ProblemsScreenState.Data).problems)
        newMap[id] = ProblemEntity(problem)
        _screenState.value = ProblemsScreenState.Data(newMap)
    }

    class Factory @Inject constructor(private val getProblemsUseCase: GetProblemsUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProblemsViewModel(getProblemsUseCase) as T
        }
    }
}
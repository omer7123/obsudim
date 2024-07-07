package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.useCase.ChangeCurrentProblem
import com.example.mypsychologist.domain.useCase.GetProblemsUseCase
import com.example.mypsychologist.ui.exercises.rebt.ProblemDelegateItem
import com.example.mypsychologist.ui.exercises.rebt.ProblemsDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProblemsViewModel(private val getProblemsUseCase: GetProblemsUseCase, private val changeCurrentProblem: ChangeCurrentProblem) : ViewModel() {

    private val _screenState: MutableStateFlow<Resource<List<ProblemEntity>>> =
        MutableStateFlow(Resource.Loading)
    val screenState: StateFlow<Resource<List<ProblemEntity>>>
        get() = _screenState.asStateFlow()

    init {
        getProblems()
    }

    fun getProblems() {
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = getProblemsUseCase()
        }
    }

    fun markAsCurrent(problemId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            changeCurrentProblem(problemId)
        }
    }

 /*   fun add(problem: String, goal: String, id: String) {
        val newList = listOf((screenState.value as ProblemsScreenState.Data).problems)
        newList[id] = ProblemEntity(problem, goal = goal)
        _screenState.value = ProblemsScreenState.Data(newList)
    } */

    class Factory @Inject constructor(private val getProblemsUseCase: GetProblemsUseCase, private val changeCurrentProblem: ChangeCurrentProblem) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProblemsViewModel(getProblemsUseCase, changeCurrentProblem) as T
        }
    }
}
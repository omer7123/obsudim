package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ProblemEntity

sealed interface ProblemsScreenState {
    data object Init: ProblemsScreenState
    data object Error: ProblemsScreenState
    data object Loading: ProblemsScreenState
    class Data(val problems: Resource<List<ProblemEntity>>): ProblemsScreenState
}
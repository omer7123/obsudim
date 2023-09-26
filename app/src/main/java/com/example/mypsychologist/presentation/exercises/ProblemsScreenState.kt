package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.domain.entity.ProblemEntity

sealed interface ProblemsScreenState {
    object Init: ProblemsScreenState
    object Error: ProblemsScreenState
    object Loading: ProblemsScreenState
    class Data(val problems: List<ProblemEntity>): ProblemsScreenState
}
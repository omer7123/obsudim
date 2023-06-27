package com.example.mypsychologist.presentation

import com.example.mypsychologist.domain.entity.ProblemEntity

sealed interface ProblemsScreenState {
    object Init: ProblemsScreenState
    object Error: ProblemsScreenState
    object Loading: ProblemsScreenState
    data class Data(val problems: List<ProblemEntity>): ProblemsScreenState
}
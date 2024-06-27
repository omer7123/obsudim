package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.ui.DelegateItem

sealed interface NewProblemScreenState {
    data object Init: NewProblemScreenState
    class Result(val id: Resource<String>): NewProblemScreenState
    class ValidationError(val textIsCorrect: Boolean, val chipsAreCorrect: Boolean): NewProblemScreenState
}
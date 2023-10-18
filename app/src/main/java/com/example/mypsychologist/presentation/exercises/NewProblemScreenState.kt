package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.ui.DelegateItem

sealed interface NewProblemScreenState {
    object Init: NewProblemScreenState
    class Success(val id: String): NewProblemScreenState
    object Error: NewProblemScreenState
    class ValidationError(val textIsCorrect: Boolean, val chipsAreCorrect: Boolean): NewProblemScreenState
}
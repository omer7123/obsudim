package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.ui.DelegateItem

sealed interface NewThoughtDiaryScreenState {
    object Init: NewThoughtDiaryScreenState
    class RequestResult(val success: Boolean): NewThoughtDiaryScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>): NewThoughtDiaryScreenState
}
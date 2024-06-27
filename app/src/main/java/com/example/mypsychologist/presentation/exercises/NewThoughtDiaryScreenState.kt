package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.ui.DelegateItem

sealed interface NewThoughtDiaryScreenState {
    data object Init: NewThoughtDiaryScreenState
    class RequestResult(val resource: Resource<String>): NewThoughtDiaryScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>): NewThoughtDiaryScreenState
}
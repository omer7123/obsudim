package com.example.mypsychologist.presentation

sealed interface NewThoughtDiaryScreenState {
    object Init: NewThoughtDiaryScreenState
    class RequestResult(val success: Boolean): NewThoughtDiaryScreenState
    class ValidationError(val fields: List<String>): NewThoughtDiaryScreenState
}
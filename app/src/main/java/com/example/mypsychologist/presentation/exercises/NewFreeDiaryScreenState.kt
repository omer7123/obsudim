package com.example.mypsychologist.presentation.exercises

sealed interface NewFreeDiaryScreenState {
    object Init: NewFreeDiaryScreenState
    class RequestResult(val success: Boolean): NewFreeDiaryScreenState
    object Error: NewFreeDiaryScreenState
}

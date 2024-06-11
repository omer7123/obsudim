package com.example.mypsychologist.presentation.exercises

sealed interface NewFreeDiaryScreenState {
    data object Init: NewFreeDiaryScreenState
    data object Loading: NewFreeDiaryScreenState
    data object Success: NewFreeDiaryScreenState
    data object Content: NewFreeDiaryScreenState
    data class Error(val msg: String): NewFreeDiaryScreenState
}

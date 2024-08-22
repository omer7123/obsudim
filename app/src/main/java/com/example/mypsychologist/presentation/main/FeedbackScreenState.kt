package com.example.mypsychologist.presentation.main

sealed interface FeedbackScreenState {
    data object Init: FeedbackScreenState
    data object Loading: FeedbackScreenState
    class UserNameSaved(val result: Boolean) : FeedbackScreenState
    class Response(val result: Boolean): FeedbackScreenState
    data object ValidationError: FeedbackScreenState
}
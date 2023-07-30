package com.example.mypsychologist.presentation

sealed interface FeedbackScreenState {
    object Init: FeedbackScreenState
    object Loading: FeedbackScreenState
    class Response(val result: Boolean): FeedbackScreenState
    object ValidationError: FeedbackScreenState
}
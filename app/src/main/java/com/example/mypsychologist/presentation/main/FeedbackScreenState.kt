package com.example.mypsychologist.presentation.main

sealed interface FeedbackScreenState {
    object Init: FeedbackScreenState
    object Loading: FeedbackScreenState
    class Response(val result: Boolean): FeedbackScreenState
    object ValidationError: FeedbackScreenState
}
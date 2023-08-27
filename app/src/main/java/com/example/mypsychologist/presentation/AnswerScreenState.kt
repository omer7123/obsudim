package com.example.mypsychologist.presentation

sealed interface AnswerScreenState {
    object Init : AnswerScreenState
    object Loading : AnswerScreenState
    class Response(val result: Boolean) : AnswerScreenState
}
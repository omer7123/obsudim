package com.example.mypsychologist.presentation.main

sealed interface PsychologistFormScreenState {
    object Init : PsychologistFormScreenState
    class RequestResult(val success: Boolean) : PsychologistFormScreenState
    class ValidationError(val fields: List<String>) : PsychologistFormScreenState
    object Loading: PsychologistFormScreenState
}
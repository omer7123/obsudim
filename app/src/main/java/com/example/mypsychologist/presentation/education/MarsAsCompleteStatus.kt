package com.example.mypsychologist.presentation.education

sealed interface MarsAsCompleteStatus {
    data class Error(val msg: String): MarsAsCompleteStatus
    data object Success: MarsAsCompleteStatus
}
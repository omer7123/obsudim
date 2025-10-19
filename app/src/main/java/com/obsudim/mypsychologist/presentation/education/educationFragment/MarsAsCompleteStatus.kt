package com.obsudim.mypsychologist.presentation.education.educationFragment

sealed interface MarsAsCompleteStatus {
    data class Error(val msg: String): MarsAsCompleteStatus
    data object Success: MarsAsCompleteStatus
}
package com.obsudim.mypsychologist.presentation.notification

sealed interface NotificationScreenState {
    data object Initial : NotificationScreenState
    data object Loading : NotificationScreenState
    data class Error(val msg: String) : NotificationScreenState

    data class Content(
        val selectedTime: String,
        val hasPermission: Boolean
    ) : NotificationScreenState
}
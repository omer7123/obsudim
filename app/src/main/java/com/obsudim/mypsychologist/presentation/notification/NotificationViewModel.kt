package com.obsudim.mypsychologist.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.useCase.notificationUseCases.GetNotificationTimeUseCase
import com.obsudim.mypsychologist.domain.useCase.notificationUseCases.SaveNotificationTimeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationViewModel @Inject constructor(
    private val getNotificationTimeUseCase: GetNotificationTimeUseCase,
    private val saveNotificationTimeUseCase: SaveNotificationTimeUseCase
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<NotificationScreenState>(NotificationScreenState.Initial)

    val screenState: StateFlow<NotificationScreenState> = _screenState

    fun loadData(hasPermission: Boolean) {
        _screenState.value = NotificationScreenState.Loading

        viewModelScope.launch {
            getNotificationTimeUseCase.invoke().collect { resource ->
                when (resource) {

                    is Resource.Success -> {
                        _screenState.value = NotificationScreenState.Content(
                            selectedTime = resource.data ?: "19:00",
                            hasPermission = hasPermission
                        )
                    }

                    is Resource.Error -> {
                        _screenState.value =
                            NotificationScreenState.Error(resource.msg ?: "Ошибка загрузки")
                    }

                    Resource.Loading -> Unit
                }
            }
        }
    }

    fun updateSelectedTime(hourOfDay: Int, minute: Int) {
        val timeString = String.format("%02d:%02d", hourOfDay, minute)

        _screenState.update { state ->
            if (state is NotificationScreenState.Content) {
                state.copy(selectedTime = timeString)
            } else state
        }

        viewModelScope.launch {
            saveNotificationTimeUseCase.invoke(timeString).collect { }
        }
    }

    class Factory @Inject constructor(
        private val getNotificationTimeUseCase: GetNotificationTimeUseCase,
        private val saveNotificationTimeUseCase: SaveNotificationTimeUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return NotificationViewModel(
                getNotificationTimeUseCase,
                saveNotificationTimeUseCase
            ) as T
        }
    }
}
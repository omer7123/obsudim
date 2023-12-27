package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.AutoDialogMessageEntity
import com.example.mypsychologist.domain.useCase.GetAutoDialogUseCase
import com.example.mypsychologist.domain.useCase.SaveAutoDialogMessageUseCase
import com.example.mypsychologist.domain.useCase.SaveBeliefVerificationUseCase
import com.example.mypsychologist.presentation.ListScreenState
import com.example.mypsychologist.ui.exercises.rebt.MessageDelegateItem
import com.example.mypsychologist.ui.exercises.rebt.toDelegateItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AutoDialogViewModel(
    private val getAutoDialogUseCase: GetAutoDialogUseCase,
    private val saveAutoDialogMessageUseCase: SaveAutoDialogMessageUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<ListScreenState> =
        MutableStateFlow(ListScreenState.Init)
    val screenState: StateFlow<ListScreenState>
        get() = _screenState.asStateFlow()

    private var dialog = listOf<MessageDelegateItem>()

    init {
        _screenState.value = ListScreenState.Loading

        viewModelScope.launch {
            _screenState.value = try {
                dialog = getAutoDialogUseCase().toDelegateItems()
                ListScreenState.Data(dialog)
            } catch (t: Throwable) {
                ListScreenState.Error
            }
        }
    }

    fun currentIsRational() =
        if (dialog.isNotEmpty())
            !dialog.last().content().second.rational
        else
            true

    fun save(message: String) {
        _screenState.value = ListScreenState.Loading

        viewModelScope.launch {
            _screenState.value = try {

                val messageEntity = AutoDialogMessageEntity(currentIsRational(), message)

                val id = saveAutoDialogMessageUseCase(messageEntity)
                val newList = dialog.map { it }.toMutableList()

                newList.add(MessageDelegateItem(id, messageEntity))
                dialog = newList
                ListScreenState.Data(dialog)

            } catch (t: Throwable) {
                ListScreenState.Error
            }
        }
    }

    class Factory @Inject constructor(
        private val getAutoDialogUseCase: GetAutoDialogUseCase,
        private val saveAutoDialogMessageUseCase: SaveAutoDialogMessageUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AutoDialogViewModel(
                getAutoDialogUseCase,
                saveAutoDialogMessageUseCase
            ) as T
        }
    }
}
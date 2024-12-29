package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.feedUseCases.GetFeedUseCase
import com.example.mypsychologist.domain.useCase.feedUseCases.PutLikeInFeedUseCase
import com.example.mypsychologist.domain.useCase.feedUseCases.RemoveLikeInFeedUseCase
import com.example.mypsychologist.domain.useCase.feedUseCases.SendMessageInFeedUseCase
import com.example.mypsychologist.ui.feed.FeedDelegateItem
import com.example.mypsychologist.ui.feed.toDelegateItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel(
    private val getFeedUseCase: GetFeedUseCase,
    private val putLikeInFeedUseCase: PutLikeInFeedUseCase,
    private val removeLikeInFeedUseCase: RemoveLikeInFeedUseCase,
    private val sendMessageInFeedUseCase: SendMessageInFeedUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<ListScreenState> =
        MutableStateFlow(ListScreenState.Init)
    val screenState: StateFlow<ListScreenState>
        get() = _screenState.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        _screenState.value = ListScreenState.Loading
        viewModelScope.launch {
            _screenState.value =
                try {
                    ListScreenState.Data(getFeedUseCase().toDelegateItem())
                } catch (t: Throwable) {
                    ListScreenState.Error
                }
        }
    }

    fun onLikeClick(itemId: String, isChecked: Boolean) {
        viewModelScope.launch {
            if (isChecked)
                putLikeInFeedUseCase(itemId)
            else
                removeLikeInFeedUseCase(itemId)
        }
    }

    fun sendMessage(message: String) {

        viewModelScope.launch {

            _screenState.value =
                if (screenState.value is ListScreenState.Data) {
                    try {
                        val newMessage = sendMessageInFeedUseCase(message)

                        val newList =
                            (screenState.value as ListScreenState.Data).items.map { it }
                                .toMutableList()

                        newList.add(0, FeedDelegateItem(newMessage))

                        ListScreenState.Data(newList)
                    } catch (t: Throwable) {
                        ListScreenState.Error
                    }
                } else {
                    ListScreenState.Error
                }
        }
    }

    class Factory @Inject constructor(
        private val getFeedUseCase: GetFeedUseCase,
        private val putLikeInFeedUseCase: PutLikeInFeedUseCase,
        private val removeLikeInFeedUseCase: RemoveLikeInFeedUseCase,
        private val sendMessageInFeedUseCase: SendMessageInFeedUseCase
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return FeedViewModel(
                getFeedUseCase,
                putLikeInFeedUseCase,
                removeLikeInFeedUseCase,
                sendMessageInFeedUseCase
            ) as T
        }
    }
}
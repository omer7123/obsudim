package com.obsudim.mypsychologist.presentation.core

import com.obsudim.mypsychologist.ui.core.delegateItems.DelegateItem

sealed interface ListScreenState {
    data object Init: ListScreenState
    data object Error:
        ListScreenState              // поменять на класс и передавать сообщение ошибки
    data object Loading: ListScreenState
    class Data(val items: List<DelegateItem>): ListScreenState
}
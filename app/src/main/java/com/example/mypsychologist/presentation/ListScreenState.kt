package com.example.mypsychologist.presentation

import com.example.mypsychologist.ui.DelegateItem

sealed interface ListScreenState {
    data object Init: ListScreenState
    data object Error: ListScreenState
    data object Loading: ListScreenState
    class Data(val items: List<DelegateItem>): ListScreenState
}
package com.example.mypsychologist.presentation

import com.example.mypsychologist.ui.DelegateItem

sealed interface ListScreenState {
    object Init: ListScreenState
    object Error: ListScreenState
    object Loading: ListScreenState
    class Data(val items: List<DelegateItem>): ListScreenState
}
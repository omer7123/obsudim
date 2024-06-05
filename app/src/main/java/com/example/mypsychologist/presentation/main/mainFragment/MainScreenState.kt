package com.example.mypsychologist.presentation.main.mainFragment

sealed class MainScreenState {

    data object Loading : MainScreenState()
    data object Success : MainScreenState()
    data class Error(val msg:String): MainScreenState()
    data object Initial: MainScreenState()
}

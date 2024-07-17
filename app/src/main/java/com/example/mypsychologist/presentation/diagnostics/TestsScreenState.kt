package com.example.mypsychologist.presentation.diagnostics

import com.example.mypsychologist.ui.DelegateItem

sealed class TestsScreenState {
    data object Initial: TestsScreenState()
    data object Loading: TestsScreenState()
    data class Error(val msg:String): TestsScreenState()
    data class Content(val data: List<DelegateItem>): TestsScreenState()
}
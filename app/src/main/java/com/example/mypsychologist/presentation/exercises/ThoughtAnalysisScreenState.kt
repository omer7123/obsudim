package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.exercises.cbt.InputDelegateItem

sealed interface ThoughtAnalysisScreenState {
    object Init: ThoughtAnalysisScreenState
    object Loading: ThoughtAnalysisScreenState
    class Data(val saved: Pair<List<InputDelegateItem>, List<InputDelegateItem>>) : ThoughtAnalysisScreenState
    class RequestResult(val success: Boolean): ThoughtAnalysisScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>) : ThoughtAnalysisScreenState
}
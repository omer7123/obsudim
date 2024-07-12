package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.exercises.cbt.InputDelegateItem

sealed interface ThoughtAnalysisScreenState {
    data object Init: ThoughtAnalysisScreenState
    data object Loading: ThoughtAnalysisScreenState
    class Data(val saved: Pair<List<InputDelegateItem>, List<InputDelegateItem>>) : ThoughtAnalysisScreenState
    class RequestResult(val result: List<Resource<String>>): ThoughtAnalysisScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>) : ThoughtAnalysisScreenState
}
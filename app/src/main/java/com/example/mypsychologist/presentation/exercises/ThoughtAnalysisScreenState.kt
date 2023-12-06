package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.exercises.cbt.ThoughtDiaryDelegateItem

sealed interface ThoughtAnalysisScreenState {
    object Init: ThoughtAnalysisScreenState
    object Loading: ThoughtAnalysisScreenState
    class Data(val saved: Pair<List<ThoughtDiaryDelegateItem>, List<ThoughtDiaryDelegateItem>>) : ThoughtAnalysisScreenState
    class RequestResult(val success: Boolean): ThoughtAnalysisScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>) : ThoughtAnalysisScreenState
}
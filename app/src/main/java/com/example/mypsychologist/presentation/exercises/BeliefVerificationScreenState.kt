package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.exercises.cbt.ThoughtDiaryDelegateItem

sealed interface BeliefVerificationScreenState {
    object Init: BeliefVerificationScreenState
    object Loading: BeliefVerificationScreenState
    class Data(val saved: List<ThoughtDiaryDelegateItem>) : BeliefVerificationScreenState
    class RequestResult(val success: Boolean): BeliefVerificationScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>) : BeliefVerificationScreenState
}
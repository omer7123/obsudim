package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.exercises.cbt.ThoughtDiaryDelegateItem

sealed interface BeliefVerificationScreenState {
    data object Init: BeliefVerificationScreenState
    data object Loading: BeliefVerificationScreenState
    class Data(val saved: List<ThoughtDiaryDelegateItem>) : BeliefVerificationScreenState
    class Error(val message: String) : BeliefVerificationScreenState
    class RequestResult(val success: Resource<String>): BeliefVerificationScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>) : BeliefVerificationScreenState
}
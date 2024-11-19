package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailWithDelegateItem
import com.example.mypsychologist.ui.DelegateItem

sealed interface NewThoughtDiaryScreenState {
    data object Init: NewThoughtDiaryScreenState
    data class Error(val msg: String): NewThoughtDiaryScreenState
    data class Content(val data: ExerciseDetailWithDelegateItem): NewThoughtDiaryScreenState
    data object Loading: NewThoughtDiaryScreenState
    class RequestResult(val resource: Resource<String>): NewThoughtDiaryScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>): NewThoughtDiaryScreenState
}
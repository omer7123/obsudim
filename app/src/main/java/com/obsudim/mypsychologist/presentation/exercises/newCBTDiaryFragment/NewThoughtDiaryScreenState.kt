package com.obsudim.mypsychologist.presentation.exercises.newCBTDiaryFragment

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.ui.core.delegateItems.DelegateItem

sealed interface NewThoughtDiaryScreenState {
  /*  data object Init: NewThoughtDiaryScreenState
    data class Error(val msg: String): NewThoughtDiaryScreenState
    data class Content(val data: ExerciseDetailWithDelegateItem): NewThoughtDiaryScreenState
    data object Loading: NewThoughtDiaryScreenState
    class RequestResult(val resource: Resource<String>): NewThoughtDiaryScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>): NewThoughtDiaryScreenState */

    data object Init: NewThoughtDiaryScreenState
    class RequestResult(val resource: Resource<String>): NewThoughtDiaryScreenState
    class ValidationError(val listWithErrors: List<DelegateItem>): NewThoughtDiaryScreenState
}
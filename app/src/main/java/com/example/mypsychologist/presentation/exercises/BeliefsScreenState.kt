package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.ui.DelegateItem

sealed interface BeliefsScreenState {
    object Init : BeliefsScreenState
    object Loading : BeliefsScreenState
    class Data(val beliefs: Map<String, String>) : BeliefsScreenState
    object Error : BeliefsScreenState
}
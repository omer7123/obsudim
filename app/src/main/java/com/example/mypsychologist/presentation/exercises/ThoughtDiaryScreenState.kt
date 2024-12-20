package com.example.mypsychologist.presentation.exercises

import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity

sealed interface ThoughtDiaryScreenState {
    object Init: ThoughtDiaryScreenState
    object Error: ThoughtDiaryScreenState
    object Loading: ThoughtDiaryScreenState
    object EditingSuccess: ThoughtDiaryScreenState
    class Data(val diary: ThoughtDiaryEntity): ThoughtDiaryScreenState
}
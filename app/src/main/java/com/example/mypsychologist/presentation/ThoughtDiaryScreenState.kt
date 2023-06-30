package com.example.mypsychologist.presentation

import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity

sealed interface ThoughtDiaryScreenState {
    object Init: ThoughtDiaryScreenState
    object Error: ThoughtDiaryScreenState
    object Loading: ThoughtDiaryScreenState
    class Data(val diary: ThoughtDiaryEntity): ThoughtDiaryScreenState
}
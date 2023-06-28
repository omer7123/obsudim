package com.example.mypsychologist.presentation

import com.example.mypsychologist.domain.entity.DiaryEntity

sealed interface ThoughtDiaryScreenState {
    object Init: ThoughtDiaryScreenState
    object Error: ThoughtDiaryScreenState
    object Loading: ThoughtDiaryScreenState
    data class Data(val diary: DiaryEntity): ThoughtDiaryScreenState
}
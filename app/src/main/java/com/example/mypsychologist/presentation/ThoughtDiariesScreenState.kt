package com.example.mypsychologist.presentation

import com.example.mypsychologist.domain.entity.DiaryRecordEntity
import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity

sealed interface ThoughtDiariesScreenState {
    object Init: ThoughtDiariesScreenState
    object Error: ThoughtDiariesScreenState
    object Loading: ThoughtDiariesScreenState
    class Data(val records: HashMap<String, String>): ThoughtDiariesScreenState
}
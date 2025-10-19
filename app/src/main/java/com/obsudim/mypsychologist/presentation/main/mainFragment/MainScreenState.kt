package com.obsudim.mypsychologist.presentation.main.mainFragment

import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity

sealed class MainScreenState {

    data object Loading : MainScreenState()
    data class Content(val tasks: List<DailyExerciseEntity>, val date: String) : MainScreenState()
    data class Error(val msgId: Int): MainScreenState()
    data object Initial: MainScreenState()
}

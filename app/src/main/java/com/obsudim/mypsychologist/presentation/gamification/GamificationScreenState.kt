package com.obsudim.mypsychologist.presentation.gamification

import com.obsudim.mypsychologist.domain.entity.gamificationEntity.CurrentScoreEntity
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.WeeklyScoresEntity
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserDataEntity

sealed interface GamificationScreenState {
    data object Initial: GamificationScreenState
    data object Loading: GamificationScreenState
    data class Error(val msg: String): GamificationScreenState
    data class Content(
        val currentScore: CurrentScoreEntity,
        val weeklyScores: WeeklyScoresEntity,
        val userInfo: UserDataEntity
    ): GamificationScreenState
}
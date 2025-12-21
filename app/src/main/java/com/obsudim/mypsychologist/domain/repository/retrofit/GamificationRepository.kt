package com.obsudim.mypsychologist.domain.repository.retrofit

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.CurrentScoreEntity
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.WeeklyScoresEntity
import kotlinx.coroutines.flow.Flow

interface GamificationRepository {
        suspend fun getCurrentScore(): Flow<Resource<CurrentScoreEntity>>
        suspend fun getWeeklyScores(): Flow<Resource<WeeklyScoresEntity>>
}
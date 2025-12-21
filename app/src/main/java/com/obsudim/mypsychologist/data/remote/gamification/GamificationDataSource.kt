package com.obsudim.mypsychologist.data.remote.gamification

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.CurrentScoreModel
import com.obsudim.mypsychologist.data.model.WeeklyScoresModel
import kotlinx.coroutines.flow.Flow

interface GamificationDataSource {
    suspend fun getCurrentScore(): Flow<Resource<CurrentScoreModel>>
    suspend fun getWeeklyScores(): Flow<Resource<WeeklyScoresModel>>
}
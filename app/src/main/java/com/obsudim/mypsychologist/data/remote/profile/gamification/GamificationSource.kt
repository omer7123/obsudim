package com.obsudim.mypsychologist.data.remote.profile.gamification

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.CurrentScoreModel
import com.obsudim.mypsychologist.data.model.WeeklyScoresModel

interface GamificationSource {
    suspend fun getCurrentScore(): Resource<CurrentScoreModel>
    suspend fun getWeeklyScores(): Resource<WeeklyScoresModel>
}
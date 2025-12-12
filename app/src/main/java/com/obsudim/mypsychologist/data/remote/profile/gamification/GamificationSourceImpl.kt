package com.obsudim.mypsychologist.data.remote.profile.gamification

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.CurrentScoreModel
import com.obsudim.mypsychologist.data.model.WeeklyScoresModel
import javax.inject.Inject

data class GamificationSourceImpl @Inject constructor(private val api: GamificationService):
    GamificationSource, BaseDataSource() {
    override suspend fun getCurrentScore(): Resource<CurrentScoreModel> = getResult {
        api.getCurrentScore()
    }

    override suspend fun getWeeklyScores(): Resource<WeeklyScoresModel> = getResult {
        api.getWeeklyScores()
    }
}


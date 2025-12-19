package com.obsudim.mypsychologist.data.remote.gamification

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.CurrentScoreModel
import com.obsudim.mypsychologist.data.model.WeeklyScoresModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class GamificationDataSourceImpl @Inject constructor(private val api: GamificationService):
    GamificationDataSource, BaseDataSource() {
    override suspend fun getCurrentScore(): Flow<Resource<CurrentScoreModel>> = flow {
        emit(
            getResult {
                api.getCurrentScore()
            }
        )
    }

    override suspend fun getWeeklyScores(): Flow<Resource<WeeklyScoresModel>> = flow {
        emit(
            getResult {
                api.getWeeklyScores()
            }
        )
    }
}


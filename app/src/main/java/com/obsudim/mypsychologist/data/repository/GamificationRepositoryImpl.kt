package com.obsudim.mypsychologist.data.repository

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.converters.toEntity
import com.obsudim.mypsychologist.data.remote.gamification.GamificationDataSource
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.CurrentScoreEntity
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.WeeklyScoresEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.GamificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GamificationRepositoryImpl @Inject constructor(
    private val dataSource: GamificationDataSource,
) :
    GamificationRepository {

    override suspend fun getCurrentScore(): Flow<Resource<CurrentScoreEntity>> =
        dataSource.getCurrentScore().checkResource {
            it.toEntity()
        }

    override suspend fun getWeeklyScores(): Flow<Resource<WeeklyScoresEntity>> =
        dataSource.getWeeklyScores().checkResource {
            it.toEntity()
        }
}
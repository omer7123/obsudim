package com.obsudim.mypsychologist.domain.useCase.gamificationCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.WeeklyScoresEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.GamificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeeklyScoresUseCase @Inject constructor(private val repository: GamificationRepository) {
    suspend operator fun invoke(): Flow<Resource<WeeklyScoresEntity>> = repository.getWeeklyScores()
}
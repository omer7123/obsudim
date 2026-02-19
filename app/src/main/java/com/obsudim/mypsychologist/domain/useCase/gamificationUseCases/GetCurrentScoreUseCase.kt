package com.obsudim.mypsychologist.domain.useCase.gamificationUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.CurrentScoreEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.GamificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentScoreUseCase @Inject constructor(private val repository: GamificationRepository) {
    suspend operator fun invoke(): Flow<Resource<CurrentScoreEntity>> = repository.getCurrentScore()
}
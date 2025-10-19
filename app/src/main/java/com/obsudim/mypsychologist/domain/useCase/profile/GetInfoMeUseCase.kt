package com.obsudim.mypsychologist.domain.useCase.profile

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserDataEntity
import com.obsudim.mypsychologist.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInfoMeUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(): Flow<Resource<UserDataEntity>> = repository.getAuthMe()
}
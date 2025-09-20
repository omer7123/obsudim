package com.example.mypsychologist.domain.useCase.profile

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.priofileEntity.UserDataEntity
import com.example.mypsychologist.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInfoMeUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(): Flow<Resource<UserDataEntity>> = repository.getAuthMe()
}
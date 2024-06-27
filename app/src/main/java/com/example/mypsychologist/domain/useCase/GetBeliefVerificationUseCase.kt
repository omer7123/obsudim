package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class GetBeliefVerificationUseCase @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(type: String): Resource<BeliefVerificationEntity> =
        repository.getBeliefVerification(type)
}
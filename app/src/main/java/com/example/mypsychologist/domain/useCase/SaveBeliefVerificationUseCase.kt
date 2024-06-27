package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class SaveBeliefVerificationUseCase @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(beliefVerificationEntity: BeliefVerificationEntity, type: String): Resource<String> =
        repository.saveBeliefVerification(beliefVerificationEntity, type)
}
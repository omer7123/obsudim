package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class GetBeliefVerificationUseCase @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(type: String) =
        repository.getBeliefVerification(type)
}
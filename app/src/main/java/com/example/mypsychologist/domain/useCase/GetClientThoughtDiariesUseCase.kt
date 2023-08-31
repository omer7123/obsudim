package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.CbtRepository
import javax.inject.Inject

class GetClientThoughtDiariesUseCase @Inject constructor(private val repository: CbtRepository) {
    suspend operator fun invoke(clientId: String) =
        repository.getThoughtDiariesFor(clientId)
}
package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.CbtRepository
import javax.inject.Inject

class GetClientThoughtDiaryUseCase @Inject constructor(private val repository: CbtRepository) {
    suspend operator fun invoke(clientId: String, id: String) =
        repository.getThoughtDiaryFor(clientId, id)
}
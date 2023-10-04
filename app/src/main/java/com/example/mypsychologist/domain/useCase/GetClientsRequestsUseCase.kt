package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.ClientRequestEntity
import com.example.mypsychologist.domain.repository.PsychologistRepository
import javax.inject.Inject

class GetClientsRequestsUseCase @Inject constructor(private val repository: PsychologistRepository) {
    suspend operator fun invoke(): List<ClientRequestEntity> =
        repository.getClientsRequests()
}
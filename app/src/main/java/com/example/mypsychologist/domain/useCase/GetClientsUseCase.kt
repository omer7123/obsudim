package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.ClientCardEntity
import com.example.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class GetClientsUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(): List<ClientCardEntity> =
        repository.getClients()
}
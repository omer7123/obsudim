package com.example.mypsychologist.domain.useCase.psychologistsUseCases

import com.example.mypsychologist.domain.entity.psychologistsEntity.SendRequestToPsychologistEntity
import com.example.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class SendRequestToManagerUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(sendRequestToPsychologistEntity: SendRequestToPsychologistEntity) =
        repository.sendRequestToPsychologist(sendRequestToPsychologistEntity)
}
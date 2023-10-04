package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.PsychologistRepository
import javax.inject.Inject

class SendAnswerToRequestUseCase @Inject constructor(private val repository: PsychologistRepository) {
    suspend operator fun invoke(accept: Boolean, clientId: String) =
        repository.sendAnswerToRequest(accept, clientId)
}
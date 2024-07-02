package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.PsychologistRepository
import javax.inject.Inject

class SendRequestToPsychologistUseCase @Inject constructor(private val repository: PsychologistRepository) {
//    operator fun invoke(psychologistId: String, text: String) =
//        repository.sendRequestTo(psychologistId, text)
}
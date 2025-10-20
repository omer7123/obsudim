package com.obsudim.mypsychologist.domain.useCase.psychologistsUseCases

import com.obsudim.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class SendRequestToPsychologistUseCase @Inject constructor(private val repository: PsychologistRepository) {
//    operator fun invoke(psychologistId: String, text: String) =
//        repository.sendRequestTo(psychologistId, text)
}
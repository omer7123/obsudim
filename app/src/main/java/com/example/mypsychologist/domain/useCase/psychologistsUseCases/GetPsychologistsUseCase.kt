package com.example.mypsychologist.domain.useCase.psychologistsUseCases

import com.example.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class GetPsychologistsUseCase @Inject constructor(private val repository: PsychologistRepository) {
//    suspend operator fun invoke() =
//        repository.getPsychologists()
}
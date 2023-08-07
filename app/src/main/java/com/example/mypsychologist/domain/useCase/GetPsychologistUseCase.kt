package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.PsychologistRepository
import javax.inject.Inject

class GetPsychologistUseCase @Inject constructor(private val repository: PsychologistRepository) {
    suspend operator fun invoke(id: String) =
        repository.getPsychologist(id)
}
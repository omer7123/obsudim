package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.PsychologistCard
import com.example.mypsychologist.domain.entity.PsychologistWithTaskCount
import com.example.mypsychologist.domain.repository.PsychologistRepository
import javax.inject.Inject

class GetOwnPsychologistsUseCase @Inject constructor(private val repository: PsychologistRepository) {
    suspend operator fun invoke(): HashMap<String, PsychologistWithTaskCount> =
        repository.getOwnPsychologists()
}
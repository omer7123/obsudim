package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.PsychologistData
import com.example.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class SavePsychologistUseCase @Inject constructor(private val repository: ProfileRepository) {
    operator fun invoke(data: PsychologistData) =
        repository.savePsychologist(data.info) && repository.savePsychologist(data.documents)
}
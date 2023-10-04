package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.EducationRepository
import javax.inject.Inject

class GetEducationMaterialUseCase @Inject constructor(private val repository: EducationRepository) {
    operator fun invoke(topic: Topic) =
        when (topic) {
            Topic.BASE -> {
                repository.getBase()
            }
            Topic.CBT_BASE -> {
                repository.getCBTBase()
            }
        }

    enum class Topic {
        BASE, CBT_BASE
    }
}
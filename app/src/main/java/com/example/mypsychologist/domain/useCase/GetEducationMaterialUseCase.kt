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
            Topic.BURNOUT->{
                repository.getBurnout()
            }
            Topic.BREATHING_TECHNIQUES->{
                repository.getBreathingTechniques()
            }
            Topic.RELAXATION_TECHNIQUES->{
                repository.getRelaxationTechniques()
            }
            Topic.COPING_STRATEGIES->{
                repository.getCopingStrategies()
            }

        }

    enum class Topic {
        BASE, CBT_BASE, BURNOUT, BREATHING_TECHNIQUES, RELAXATION_TECHNIQUES,COPING_STRATEGIES
    }
}
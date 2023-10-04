package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.EducationTopicEntity
import com.example.mypsychologist.domain.repository.EducationRepository
import javax.inject.Inject

class GetEducationTopicsUseCase @Inject constructor(private val repository: EducationRepository) {
    suspend operator fun invoke(): List<EducationTopicEntity> =
        repository.getTopics()
}
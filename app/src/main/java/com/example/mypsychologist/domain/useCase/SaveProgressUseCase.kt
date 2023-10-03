package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.EducationRepository
import javax.inject.Inject

class SaveProgressUseCase @Inject constructor(private val repository: EducationRepository) {
    suspend operator fun invoke(topic: String, progress: Int) {
        repository.saveProgress(topic, progress)
    }
}
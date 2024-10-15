package com.example.mypsychologist.domain.useCase.retrofitUseCase.educationUseCases

import com.example.mypsychologist.domain.repository.EducationRepository
import javax.inject.Inject

class GetMaterialOfTopicUseCase @Inject constructor(private val repository: EducationRepository) {
    suspend operator fun invoke(id: String) = repository.getEducationMaterial(id)
}
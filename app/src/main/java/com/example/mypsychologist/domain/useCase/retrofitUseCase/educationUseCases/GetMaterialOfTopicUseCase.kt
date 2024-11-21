package com.example.mypsychologist.domain.useCase.retrofitUseCase.educationUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.educationEntity.EducationsEntity
import com.example.mypsychologist.domain.repository.EducationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMaterialOfTopicUseCase @Inject constructor(private val repository: EducationRepository) {
    suspend operator fun invoke(id: String): Flow<Resource<EducationsEntity>> = repository.getEducationMaterial(id)
}
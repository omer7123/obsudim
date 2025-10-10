package com.example.mypsychologist.domain.useCase.educationUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.educationEntity.TopicEntity
import com.example.mypsychologist.domain.repository.retrofit.EducationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMaterialOfTopicUseCase @Inject constructor(private val repository: EducationRepository) {
    suspend operator fun invoke(id: String): Flow<Resource<TopicEntity>> = repository.getEducationMaterial(id)
}
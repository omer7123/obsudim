package com.obsudim.mypsychologist.domain.useCase.educationUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.educationEntity.TopicEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.EducationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMaterialOfTopicUseCase @Inject constructor(private val repository: EducationRepository) {
    suspend operator fun invoke(id: String): Flow<Resource<TopicEntity>> = repository.getEducationMaterial(id)
}
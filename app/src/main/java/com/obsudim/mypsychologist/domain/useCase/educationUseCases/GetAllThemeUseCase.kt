package com.obsudim.mypsychologist.domain.useCase.educationUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.educationEntity.TopicEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.EducationRepository
import javax.inject.Inject

class GetAllThemeUseCase @Inject constructor(private val repository: EducationRepository) {
    suspend operator fun invoke(): Resource<List<TopicEntity>> = repository.getAllTheme()
}
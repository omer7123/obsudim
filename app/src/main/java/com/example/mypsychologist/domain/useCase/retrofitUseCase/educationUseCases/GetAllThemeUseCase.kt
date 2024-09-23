package com.example.mypsychologist.domain.useCase.retrofitUseCase.educationUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.educationEntity.ThemeEntity
import com.example.mypsychologist.domain.repository.EducationRepository
import javax.inject.Inject

class GetAllThemeUseCase @Inject constructor(private val repository: EducationRepository) {
    suspend operator fun invoke(): Resource<List<ThemeEntity>> = repository.getAllTheme()
}
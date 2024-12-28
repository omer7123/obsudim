package com.example.mypsychologist.domain.useCase.educationUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.EduSaveResp
import com.example.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.example.mypsychologist.domain.repository.retrofit.EducationRepository
import javax.inject.Inject

class SaveProgressUseCase @Inject constructor(private val repository: EducationRepository) {
    suspend operator fun invoke(materialId: EducationMaterialForSaveProgressEntity): Resource<EduSaveResp> =
        repository.saveProgress(materialId)

}
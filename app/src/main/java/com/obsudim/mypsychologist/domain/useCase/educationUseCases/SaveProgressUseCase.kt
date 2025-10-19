package com.obsudim.mypsychologist.domain.useCase.educationUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.EduSaveResp
import com.obsudim.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.EducationRepository
import javax.inject.Inject

class SaveProgressUseCase @Inject constructor(private val repository: EducationRepository) {
    suspend operator fun invoke(materialId: EducationMaterialForSaveProgressEntity): Resource<EduSaveResp> =
        repository.saveProgress(materialId)

}
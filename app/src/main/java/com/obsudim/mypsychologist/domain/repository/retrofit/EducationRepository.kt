package com.obsudim.mypsychologist.domain.repository.retrofit

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.EduSaveResp
import com.obsudim.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.obsudim.mypsychologist.domain.entity.educationEntity.TopicEntity
import kotlinx.coroutines.flow.Flow

interface EducationRepository {
    suspend fun getAllTheme(): Resource<List<TopicEntity>>
    suspend fun getEducationMaterial(id: String): Flow<Resource<TopicEntity>>
    suspend fun saveProgress(educationMaterialForSaveProgressEntity: EducationMaterialForSaveProgressEntity): Resource<EduSaveResp>

}
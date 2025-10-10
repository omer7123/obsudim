package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.EduSaveResp
import com.example.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.example.mypsychologist.domain.entity.educationEntity.TopicEntity
import kotlinx.coroutines.flow.Flow

interface EducationRepository {
    suspend fun getAllTheme(): Resource<List<TopicEntity>>
    suspend fun getEducationMaterial(id: String): Flow<Resource<TopicEntity>>
    suspend fun saveProgress(educationMaterialForSaveProgressEntity: EducationMaterialForSaveProgressEntity): Resource<EduSaveResp>

}
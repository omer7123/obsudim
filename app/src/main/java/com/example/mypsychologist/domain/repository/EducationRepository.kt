package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.EduSaveResp
import com.example.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.example.mypsychologist.domain.entity.educationEntity.EducationsEntity
import com.example.mypsychologist.domain.entity.educationEntity.ThemeEntity
import kotlinx.coroutines.flow.Flow

interface EducationRepository {
    suspend fun getAllTheme(): Resource<List<ThemeEntity>>
    suspend fun getEducationMaterial(id: String): Flow<Resource<EducationsEntity>>
    suspend fun saveProgress(educationMaterialForSaveProgressEntity: EducationMaterialForSaveProgressEntity): Resource<EduSaveResp>

}
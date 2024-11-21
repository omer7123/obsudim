package com.example.mypsychologist.data.remote.education

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.EduSaveResp
import com.example.mypsychologist.data.model.EducationMaterialForSaveProgressModel
import com.example.mypsychologist.data.model.EducationsModel
import com.example.mypsychologist.data.model.ThemeModel
import kotlinx.coroutines.flow.Flow

interface EducationDataSource {
    suspend fun getAllTheme(): Resource<List<ThemeModel>>
    suspend fun getEducationMaterial(id: String): Flow<Resource<EducationsModel>>
    suspend fun saveProgress(educationMaterialForSaveProgressModel: EducationMaterialForSaveProgressModel): Resource<EduSaveResp>
}
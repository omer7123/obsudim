package com.obsudim.mypsychologist.data.remote.education

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.EduSaveResp
import com.obsudim.mypsychologist.data.model.EducationMaterialForSaveProgressModel
import com.obsudim.mypsychologist.data.model.ThemeModel
import kotlinx.coroutines.flow.Flow

interface EducationDataSource {
    suspend fun getAllTheme(): Resource<List<ThemeModel>>
    suspend fun getEducationMaterial(id: String): Flow<Resource<ThemeModel>>
    suspend fun saveProgress(educationMaterialForSaveProgressModel: EducationMaterialForSaveProgressModel): Resource<EduSaveResp>
}
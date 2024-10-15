package com.example.mypsychologist.data.remote.education

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.EduSaveResp
import com.example.mypsychologist.data.model.EducationMaterialForSaveProgressModel
import com.example.mypsychologist.data.model.ItemMaterialModel
import com.example.mypsychologist.data.model.ThemeModel

interface EducationDataSource {
    suspend fun getAllTheme(): Resource<List<ThemeModel>>
    suspend fun getEducationMaterial(id: String): Resource<List<ItemMaterialModel>>
    suspend fun saveProgress(educationMaterialForSaveProgressModel: EducationMaterialForSaveProgressModel): Resource<EduSaveResp>
}
package com.example.mypsychologist.data.remote.education

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.EducationMaterialForSaveProgressModel
import com.example.mypsychologist.data.model.ItemMaterialModel
import com.example.mypsychologist.data.model.ThemeModel
import javax.inject.Inject

class EducationDataSourceImpl @Inject constructor(private val api: EducationService) :
    EducationDataSource,
    BaseDataSource() {
    override suspend fun getAllTheme(): Resource<List<ThemeModel>> = getResult {
        api.getAllTheme()
    }

    override suspend fun getEducationMaterial(id: String): Resource<List<ItemMaterialModel>> =getResult{
        api.getEducationMaterial(id)
    }

    override suspend fun saveProgress(educationMaterialForSaveProgressModel: EducationMaterialForSaveProgressModel) = getResult{
        api.saveProgress(educationMaterialForSaveProgressModel)
    }
}
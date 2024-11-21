package com.example.mypsychologist.data.remote.education

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.EducationMaterialForSaveProgressModel
import com.example.mypsychologist.data.model.EducationsModel
import com.example.mypsychologist.data.model.ThemeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EducationDataSourceImpl @Inject constructor(private val api: EducationService) :
    EducationDataSource,
    BaseDataSource() {
    override suspend fun getAllTheme(): Resource<List<ThemeModel>> = getResult {
        api.getAllTheme()
    }

    override suspend fun getEducationMaterial(id: String): Flow<Resource<EducationsModel>> = flow {
        emit(
            getResult {
                api.getEducationMaterial(id)
            }
        )
    }

    override suspend fun saveProgress(educationMaterialForSaveProgressModel: EducationMaterialForSaveProgressModel) = getResult{
        api.saveProgress(educationMaterialForSaveProgressModel)
    }
}
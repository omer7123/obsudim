package com.obsudim.mypsychologist.data.remote.education

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.EducationMaterialForSaveProgressModel
import com.obsudim.mypsychologist.data.model.ThemeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EducationDataSourceImpl @Inject constructor(private val api: EducationService) :
    EducationDataSource,
    BaseDataSource() {
    override suspend fun getAllTheme(): Resource<List<ThemeModel>> = getResult {
        api.getAllTheme()
    }

    override suspend fun getEducationMaterial(id: String): Flow<Resource<ThemeModel>> = flow {
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
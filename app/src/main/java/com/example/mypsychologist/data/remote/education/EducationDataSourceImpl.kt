package com.example.mypsychologist.data.remote.education

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.ThemeModel
import javax.inject.Inject

class EducationDataSourceImpl @Inject constructor(private val api: EducationService) :
    EducationDataSource,
    BaseDataSource() {
    override suspend fun getAllTheme(): Resource<List<ThemeModel>> = getResult {
        api.getAllTheme()
    }
}
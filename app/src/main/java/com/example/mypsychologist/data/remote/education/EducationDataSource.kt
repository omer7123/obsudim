package com.example.mypsychologist.data.remote.education

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.ThemeModel

interface EducationDataSource {
    suspend fun getAllTheme(): Resource<List<ThemeModel>>
}
package com.example.mypsychologist.data.remote.education

import com.example.mypsychologist.data.model.ThemeModel
import retrofit2.Response
import retrofit2.http.GET

interface EducationService {

    @GET("/education/get_all_theme")
    suspend fun getAllTheme(): Response<List<ThemeModel>>
}
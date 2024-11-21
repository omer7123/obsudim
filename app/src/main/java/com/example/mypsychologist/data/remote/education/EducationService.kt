package com.example.mypsychologist.data.remote.education

import com.example.mypsychologist.data.model.EduSaveResp
import com.example.mypsychologist.data.model.EducationMaterialForSaveProgressModel
import com.example.mypsychologist.data.model.EducationsModel
import com.example.mypsychologist.data.model.ThemeModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EducationService {

    @GET("/education/get_all_theme")
    suspend fun getAllTheme(): Response<List<ThemeModel>>

    @GET("/education/get_all_education_material/{education_theme_id}")
    suspend fun getEducationMaterial(@Path("education_theme_id") topicId: String): Response<EducationsModel>

    @POST("/education/complete_education_material")
    suspend fun saveProgress(@Body material: EducationMaterialForSaveProgressModel): Response<EduSaveResp>
}
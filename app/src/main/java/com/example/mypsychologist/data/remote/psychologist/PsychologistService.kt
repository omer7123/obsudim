package com.example.mypsychologist.data.remote.psychologist

import com.example.mypsychologist.data.model.ManagerModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PsychologistService {

    @GET("/client/get_list_get_psycholog")
    fun getManagersList(): Response<List<ManagerModel>>

    @POST("/client/get_list_get_psycholog")
    fun getManager(@Body user_id: String): Response<ManagerModel>
}
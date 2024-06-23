package com.example.mypsychologist.data.remote.psychologist

import com.example.mypsychologist.data.model.ManagerModel
import com.example.mypsychologist.data.model.TaskModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PsychologistService {

    @GET("/client/get_list_get_psycholog")
    suspend fun getManagersList(): Response<List<ManagerModel>>

    @POST("/client/get_list_get_psycholog")
    suspend fun getManager(@Body user_id: String): Response<ManagerModel>

    @POST("/client/get_tasks")
    suspend fun getTasks(): Response<List<TaskModel>>

    @POST("/client/complete_task")
    suspend fun markTaskAsCompleted(@Body task_id: String): Response<String>

    @POST("/client/unfulfilled_task")
    suspend fun markTaskAsUnfulfilled(@Body task_id: String): Response<String>
}
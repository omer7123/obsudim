package com.obsudim.mypsychologist.data.remote.psychologist

import com.obsudim.mypsychologist.data.model.ManagerModel
import com.obsudim.mypsychologist.data.model.MyPsychologistModel
import com.obsudim.mypsychologist.data.model.TaskIdModel
import com.obsudim.mypsychologist.data.model.TaskModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PsychologistService {

    @GET("/manager/get_all_manager")
    suspend fun getManagersList(): Response<List<ManagerModel>>

    @GET("/client/get_your_psychologist")
    suspend fun getYourPsychologist(): Response<List<MyPsychologistModel>>

    @POST("/client/get_list_get_psycholog")
    suspend fun getManager(@Body user_id: String): Response<ManagerModel>

    @GET("/client/get_tasks")
    suspend fun getTasks(): Response<List<TaskModel>>

    @POST("/client/complete_task")
    suspend fun markTaskAsCompleted(@Body taskId: TaskIdModel): Response<String>

    @POST("/client/unfulfilled_task")
    suspend fun markTaskAsUnfulfilled(@Body taskId: TaskIdModel): Response<String>

//    @GET("/client/get_your_psychologist")
//    suspend fun getMyPsychologists(): Response

}
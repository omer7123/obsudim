package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.data.model.ProblemModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProblemService {
    @POST("/problem/new_problem")
    suspend fun saveNewProblem(@Body problem: ProblemModel): Response<String>

    @GET("/problem/get_all_problems/{user_id}")
    suspend fun getProblems(@Path(value = "user_id") userId: String): Response<List<ProblemModel>>
}
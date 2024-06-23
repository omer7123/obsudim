package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.data.model.ProblemModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ProblemService {
    @POST("/problem/new_problem")
    suspend fun saveNewProblem(@Body problem: ProblemModel): Response<String>
}
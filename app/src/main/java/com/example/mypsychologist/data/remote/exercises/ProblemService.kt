package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.data.model.GetProblemModel
import com.example.mypsychologist.data.model.ProblemAnalysisModel
import com.example.mypsychologist.data.model.SaveProblemModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProblemService {
    @POST("/problem/new_problem")
    suspend fun saveNewProblem(@Body problem: SaveProblemModel): Response<String>

    @GET("/problem/get_all_problems/{user_id}")
    suspend fun getProblems(@Path("user_id") userId: String): Response<List<GetProblemModel>>

    @POST("/belief/create_deep_conviction")
    suspend fun saveProblemAnalysisItem(@Body model: ProblemAnalysisModel): Response<String>

    @GET("/problem/get_analysis/{problem_id}")
    suspend fun getProblemAnalysis(@Path("problem_id") problemId: String): Response<List<ProblemAnalysisModel>>


}
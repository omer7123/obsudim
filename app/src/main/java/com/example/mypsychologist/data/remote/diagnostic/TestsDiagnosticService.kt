package com.example.mypsychologist.data.remote.diagnostic

import com.example.mypsychologist.data.model.ConclusionOfTestModel
import com.example.mypsychologist.data.model.QuestionOfTestModel
import com.example.mypsychologist.data.model.SaveTestResultModel
import com.example.mypsychologist.data.model.TestInfoModel
import com.example.mypsychologist.data.model.TestModel
import com.example.mypsychologist.data.model.TestResultsGetModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TestsDiagnosticService {

    @GET("/test/get_all_tests")
    suspend fun getAllTests(): Response<List<TestModel>>

    @POST("/test/save_test_result")
    suspend fun saveTestResult(@Body saveTestResultModel: SaveTestResultModel): Response<List<ConclusionOfTestModel>>

    @GET("/test/get_test_results/{test_id}")
    suspend fun getTestResults(
        @Path("test_id") testId: String,
        @Query("user_id") userId: String
    ): Response<List<TestResultsGetModel>>

    @GET("/test/get_test_info/{id}")
    suspend fun getTestInfo(
        @Path("id") testId: String
    ): Response<TestInfoModel>

    @GET("/test/get_test_questions/{test_id}")
    suspend fun getQuestionsOfTest(
        @Path("test_id") testId: String
    ): Response<List<QuestionOfTestModel>>

    @GET("/test/get_test_result/{test_result_id}")
    suspend fun getTestResult(
        @Path("test_result_id") testResultId: String
    ): Response<TestResultsGetModel>
}
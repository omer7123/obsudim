package com.obsudim.mypsychologist.data.remote.diagnostic

import com.obsudim.mypsychologist.data.model.QuestionOfTestModel
import com.obsudim.mypsychologist.data.model.ResultAfterSaveModel
import com.obsudim.mypsychologist.data.model.SaveTestResultModel
import com.obsudim.mypsychologist.data.model.TestInfoModel
import com.obsudim.mypsychologist.data.model.TestModel
import com.obsudim.mypsychologist.data.model.TestResultsGetModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TestsDiagnosticService {

    @GET("/tests")
    suspend fun getAllTests(): Response<List<TestModel>>

    @POST("/tests/result")
    suspend fun saveTestResult(@Body saveTestResultModel: SaveTestResultModel): Response<ResultAfterSaveModel>

    @GET("/tests/{test_id}/results/")
    suspend fun getTestResults(
        @Path("test_id") testId: String,
    ): Response<List<TestResultsGetModel>>

    @GET("/tests/{test_id}")
    suspend fun getTestInfo(
        @Path("test_id") testId: String
    ): Response<TestInfoModel>

    @GET("/tests/{test_id}/questions/answers")
    suspend fun getQuestionsOfTest(
        @Path("test_id") testId: String
    ): Response<List<QuestionOfTestModel>>

    @GET("/tests/test_result/{result_id}")
    suspend fun getTestResult(
        @Path("result_id") testResultId: String
    ): Response<TestResultsGetModel>
}
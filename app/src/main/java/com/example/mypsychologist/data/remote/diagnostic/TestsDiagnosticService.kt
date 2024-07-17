package com.example.mypsychologist.data.remote.diagnostic

import com.example.mypsychologist.data.model.SaveTestResultModel
import com.example.mypsychologist.data.model.TestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TestsDiagnosticService {

    @GET("/test/get_all_tests")
    suspend fun getAllTests(): Response<List<TestModel>>

    @POST("/test/save_test_result")
    suspend fun saveTestResult(@Body saveTestResultModel: SaveTestResultModel): Response<String>
}
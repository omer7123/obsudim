package com.example.mypsychologist.data.remote.diagnostic

import com.example.mypsychologist.data.model.TestModel
import retrofit2.Response
import retrofit2.http.GET

interface TestsDiagnosticService {

    @GET("/test/get_all_tests")
    suspend fun getAllTests(): Response<List<TestModel>>
}
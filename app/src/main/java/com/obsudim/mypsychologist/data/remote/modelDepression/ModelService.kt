package com.obsudim.mypsychologist.data.remote.modelDepression

import com.obsudim.mypsychologist.data.model.ModelDepressionPostReq
import com.obsudim.mypsychologist.data.model.ModelDepressionResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ModelService {

    @POST("/predict")
    suspend fun postNote(@Body note: ModelDepressionPostReq): Response<ModelDepressionResp>
}
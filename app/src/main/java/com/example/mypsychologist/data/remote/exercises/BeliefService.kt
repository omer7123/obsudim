package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.data.model.BeliefCheckModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BeliefService {
    @POST("/belief/save_belief_check")
    fun saveBeliefCheck(@Body it: BeliefCheckModel): Response<String>

    @POST("/belief/get_belief_check")
    fun getBeliefCheck(@Body intermediate_conviction_id: String): Response<String>
}
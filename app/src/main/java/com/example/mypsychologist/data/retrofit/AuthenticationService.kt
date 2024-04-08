package com.example.mypsychologist.data.retrofit

import retrofit2.http.POST

interface AuthenticationService {
    @POST("")
    suspend fun register()
}
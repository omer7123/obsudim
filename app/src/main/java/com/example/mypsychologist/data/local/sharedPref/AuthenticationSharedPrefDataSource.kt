package com.example.mypsychologist.data.local.sharedPref

interface AuthenticationSharedPrefDataSource {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String
}
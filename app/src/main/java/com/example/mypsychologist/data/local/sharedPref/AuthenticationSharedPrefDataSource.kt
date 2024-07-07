package com.example.mypsychologist.data.local.sharedPref

interface AuthenticationSharedPrefDataSource {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String
    suspend fun getUserId(): String
    suspend fun saveUserId(userId: String)
    suspend fun saveStatusRequestToManager()
    suspend fun getStatusRequestToManager(): Boolean
}
package com.example.mypsychologist.data.local.sharedPref

interface AuthenticationSharedPrefDataSource {
    suspend fun saveToken(token: String)
    suspend fun saveRefreshToken(refToken: String)
    suspend fun getRefreshToken(): String
    suspend fun getToken(): String
    suspend fun deleteToken()
    suspend fun deleteUserId()
    suspend fun getUserId(): String
    suspend fun saveUserId(userId: String)
    suspend fun saveStatusRequestToManager()
    suspend fun getStatusRequestToManager(): Boolean

}
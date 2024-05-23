package com.example.mypsychologist.data.local.sharedPref

import javax.inject.Inject

interface AuthenticationSharedPrefDataSource {
    suspend fun saveToken(token: String)
}
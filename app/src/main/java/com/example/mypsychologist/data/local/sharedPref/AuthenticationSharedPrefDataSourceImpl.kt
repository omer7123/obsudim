package com.example.mypsychologist.data.local.sharedPref

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AuthenticationSharedPrefDataSourceImpl @Inject constructor(private val context: Context) :
    AuthenticationSharedPrefDataSource {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("auth_pref", Context.MODE_PRIVATE)

    override suspend fun saveToken(token: String) {
        sharedPref.edit().apply {
            putString(TOKEN, token)
            apply()
        }
    }

    companion object {
        private const val TOKEN = "token"
    }
}
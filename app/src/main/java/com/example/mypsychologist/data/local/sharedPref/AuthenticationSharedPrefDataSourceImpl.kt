package com.example.mypsychologist.data.local.sharedPref

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationSharedPrefDataSourceImpl @Inject constructor(context: Context) :
    AuthenticationSharedPrefDataSource {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPref = EncryptedSharedPreferences.create(
        FILE_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveToken(token: String) {
        withContext(Dispatchers.IO) {
            sharedPref.edit().apply {
                putString(TOKEN, token)
                apply()
            }
        }
    }

    override suspend fun getToken(): String {
        return sharedPref.getString(TOKEN, "").toString()
    }

    override suspend fun getUserId(): String {
        return sharedPref.getString(TOKEN, "").toString()
    }

    override suspend fun saveUserId(userId: String) {
        withContext(Dispatchers.IO) {
            sharedPref.edit().putString(USER_ID, userId).apply()
        }
    }

    companion object {
        private const val TOKEN = "token"
        private const val FILE_NAME = "auth_pref"
        private const val USER_ID = "user_id"
    }
}
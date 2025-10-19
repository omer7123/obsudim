package com.obsudim.mypsychologist.data.local.sharedPref

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

    override suspend fun saveRefreshToken(refToken: String) {
        withContext(Dispatchers.IO){
            sharedPref.edit().apply{
                putString(REF_TOKEN, refToken)
                apply()
            }
        }
    }

    override suspend fun getRefreshToken(): String {
        return sharedPref.getString(REF_TOKEN, "").toString()
    }

    override suspend fun getToken(): String {
        return sharedPref.getString(TOKEN, "").toString()
    }

    override suspend fun deleteToken() {
        withContext(Dispatchers.IO){
            sharedPref.edit().apply {
                putString(TOKEN, "")
                apply()
            }
        }
    }

    override suspend fun deleteUserId() {
        withContext(Dispatchers.IO){
            sharedPref.edit().apply {
                putString(USER_ID, "")
                apply()
            }
        }
    }

    override suspend fun getUserId(): String {
        return sharedPref.getString(USER_ID, "").toString()
    }

    override suspend fun saveUserId(userId: String) {
        withContext(Dispatchers.IO) {
            sharedPref.edit().putString(USER_ID, userId).apply()
        }
    }

    override suspend fun saveStatusRequestToManager() {
        withContext(Dispatchers.IO){
            sharedPref.edit().putBoolean(REQUEST_TO_MANAGER_STATUS, true).apply()
        }
    }

    override suspend fun getStatusRequestToManager(): Boolean {
        return sharedPref.getBoolean(REQUEST_TO_MANAGER_STATUS, false)
    }

    companion object {
        private const val TOKEN = "token"
        private const val REF_TOKEN = "ref_token"
        private const val FILE_NAME = "auth_pref"
        private const val USER_ID = "user_id"
        private const val REQUEST_TO_MANAGER_STATUS = "status"
    }
}
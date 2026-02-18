package com.obsudim.mypsychologist.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.obsudim.mypsychologist.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("notification_preferences")

@Singleton
class NotificationPreferencesRepository @Inject constructor(
    private val context: Context
) {
    companion object {
        private val NOTIFICATION_TIME = stringPreferencesKey("notification_time")
        private const val DEFAULT_TIME = "19:00"
    }

    fun getNotificationTime(): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        context.dataStore.data
            .map { preferences ->
                preferences[NOTIFICATION_TIME] ?: DEFAULT_TIME
            }
            .collect { time ->
                emit(Resource.Success(time))
            }
    }.catch { e ->
        emit(Resource.Error(e.message, null))
    }

    fun saveNotificationTime(time: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_TIME] = time
        }
        emit(Resource.Success(Unit))
    }.catch { e ->
        emit(Resource.Error(e.message, null))
    }
}
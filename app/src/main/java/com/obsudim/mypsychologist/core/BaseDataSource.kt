package com.obsudim.mypsychologist.core

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.Response

abstract class BaseDataSource {

    @Serializable
    data class ErrorResponse(val detail: String)

    protected suspend fun <T : Any> getResult(call: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = call()
            Log.d("response code",response.code().toString())

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("body", it.toString())
                    Resource.Success(it)
                } ?: Resource.Error(response.message(), null)
            } else {
                val errorBody = response.errorBody()?.use { it.string() } ?: "Unknown error"
                val errorResponse = try {
                    Json.decodeFromString<ErrorResponse>(errorBody)
                } catch (e: SerializationException) {
                    ErrorResponse("Unknown error")
                }

                Resource.Error(errorResponse.detail, null)
            }
        } catch (e: Exception) {
            Log.e("response catch error", e.toString())
            Resource.Error(e.message ?: e.toString(), null)
        }
    }
}
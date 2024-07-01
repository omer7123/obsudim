package com.example.mypsychologist.core

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.lang.Exception

abstract class BaseDataSource {

    @Serializable
    data class ErrorResponse(val detail: String)

    protected suspend fun <T : Any> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()

                return if (body != null || response.code() in 200..299) {
                    Resource.Success(body!!)
                } else {
                    Resource.Error(response.message(), response.body())
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                val errorResponse = try {
                    Json.decodeFromString<ErrorResponse>(errorBody)
                } catch (e: SerializationException) {
                    ErrorResponse("Unknown error")
                }

                return Resource.Error(errorResponse.detail, null)
            }
        } catch (e: Exception) {
            return Resource.Error(e.message ?: e.toString(), null)
        }
    }
}
package com.example.mypsychologist.core

sealed class Resource<out T: Any> {
    data class Success<out T: Any>(val data: Any?) : Resource<T>()
    data class Error<out T: Any>(val msg: String?, val data: Any?) : Resource<T>()
    data object Loading : Resource<Nothing>()

}
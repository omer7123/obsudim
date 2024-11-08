package com.example.mypsychologist.presentation.core

sealed class BaseStateUI<T> {
    class Initial<T>: BaseStateUI<T>()
    class Loading<T>: BaseStateUI<T>()
    data class Error<T>(val msg: String): BaseStateUI<T>()
    data class Content<T>(val data: T): BaseStateUI<T>()
}
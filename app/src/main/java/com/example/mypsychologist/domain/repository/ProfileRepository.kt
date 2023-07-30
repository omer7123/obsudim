package com.example.mypsychologist.domain.repository

interface ProfileRepository {
    suspend fun deleteAccount(): Boolean
    fun sendFeedback(text: String): Boolean
}
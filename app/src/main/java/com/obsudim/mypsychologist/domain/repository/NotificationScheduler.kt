package com.obsudim.mypsychologist.domain.repository

interface NotificationScheduler {

    suspend fun schedule(time: String)
}
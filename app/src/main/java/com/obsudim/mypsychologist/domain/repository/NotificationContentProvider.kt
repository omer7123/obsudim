package com.obsudim.mypsychologist.domain.repository

import com.obsudim.mypsychologist.domain.entity.NotificationContent

interface NotificationContentProvider {
    fun getNotification(): NotificationContent
}
package com.obsudim.mypsychologist.domain.repository

interface NotificationSender{
    fun send(title: String, text: String)
}
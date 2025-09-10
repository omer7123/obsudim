package com.example.mypsychologist.core

import java.util.concurrent.ConcurrentHashMap

object CookieStorage {

    private val cookieStore = ConcurrentHashMap<String, String>()
    fun saveCookies(cookies: List<String>) {
        cookies.forEach { cookie ->
            val parts = cookie.split(";")[0].split("=")
            if (parts.size == 2) {
                cookieStore[parts[0].trim()] = parts[1].trim()
            }
        }
    }

    fun getCookies(): List<String> {
        return cookieStore.map { "${it.key}=${it.value}" }
    }

}
package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.FeedItemUI

interface FeedRepository {
    suspend fun getFeed(): List<FeedItemUI>
    suspend fun like(itemId: String): Boolean
    suspend fun removeLike(itemId: String): Boolean
    suspend fun send(message: String): FeedItemUI
}
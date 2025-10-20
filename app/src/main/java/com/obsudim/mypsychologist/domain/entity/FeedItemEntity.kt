package com.obsudim.mypsychologist.domain.entity

data class FeedItemEntity(
    val authorId: String = "",
    val author: String = "",
    val date: Long = 0,
    val text: String = "",
    val likeScore: Int = 0
)

fun FeedItemEntity.toUI(id: String, iLiked: Boolean) =
    FeedItemUI(id, this, iLiked)
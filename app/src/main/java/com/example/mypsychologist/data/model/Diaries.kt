package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FreeDiaryModel(
    @SerialName("id")
    val freeDiaryId: String,
    val text: String,
    @SerialName("created_at")
    val createdAt: String
)

@Serializable
data class NewFreeDiaryModel (
    val text: String
)

@Serializable
data class NewFreeDiaryWithDateModel(
    val text: String,
    @SerialName("created_at")
    val createdAt: String
)

@Serializable
data class SaveMoodModel(
    val score: Int,
    @SerialName("emoji_ids")
    val emojiIds: List<Int>,
)
@Serializable
data class SaveMoodWithDateModel(
    val score: Int,
    val day: String,
    @SerialName("emoji_ids")
    val emojiIds: List<Int>
)

@Serializable
data class MoodTrackerPresentModel(
    val id: String,
    val score: Int,
    @SerialName("created_at")
    val date: String,
    @SerialName("emoji_ids")
    val emojiIds: List<Int>,
    @SerialName("emoji_texts")
    val emojiTexts: List<String>,
)

@Serializable
data class MoodTrackerRespModel(
    val status: String
)

@Serializable
data class CalendarResponseModel(
    val date: Int,
    val diary: Boolean,
)

@Serializable
data class EmojiModel(
    val id: Int,
    val text: String,
)


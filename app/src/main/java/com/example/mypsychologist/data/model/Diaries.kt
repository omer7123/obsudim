package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class FreeDiaryModel(
    @SerialName("free_diary_id")
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
    @SerialName("free_diary_id")
    val freeDiaryId: String = "",
    @SerialName("think_diary_id")
    val thinkDiaryId: String = "",
    val diaryType: Int = 0
)
@Serializable
data class SaveMoodWithDateModel(
    val score: Int,
    val date: String
)

@Serializable
data class MoodTrackerPresentModel(
    val id: String,
    val score: Int,
    val date: String
)

@Serializable
data class MoodTrackerRespModel(
    @SerialName("mood_tracker_id")
    val moodTrackerId: String
)

@Serializable
data class CalendarResponseModel(
    val date: Int,
    val diary: Boolean,
)

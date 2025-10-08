package com.example.mypsychologist.domain.entity.diaryEntity

import java.util.Date

data class FreeDiaryEntity (
    val id: String,
    val text: String,
    val createdAt: String,
)
data class NewFreeDiaryEntity (
    val text: String
)

data class NewFreeDiaryWithDateEntity (
    val text: String,
    val createdAt: String,
)

data class SaveMoodEntity(
    val score: Int,
    val emojiIds: List<Int>
)

data class SaveMoodWithDateEntity(
    val score: Int,
    val date: String,
    val emojiIds: List<Int>,
)

data class MoodPresentEntity(
    val id: String,
    val score: Int,
    val moodTitleResStr: Int,
    val date: String,
    val emojiTexts: List<String>,
)

data class MoodTrackerResultEntity(
    val id: String,
    val score: Int,
    val date: String,
    val emojiIds: List<Int>,
    val emojiTexts: List<String>,
)

data class MoodTrackerRespEntity(
    val status: String
)

data class CalendarEntity(
    val date: Date,
    val signal: Boolean,
    val diary: Boolean
)

data class CalendarResponseEntity(
    val date: Int,
    val diary: Boolean
)

data class EmojiEntity(
    val id: Int,
    val text: String,
)
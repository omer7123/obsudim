package com.example.mypsychologist.domain.entity.diaryEntity

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
    val freeDiaryId: String = "",
    val thinkDiaryId: String = "",
    val diaryType: Int = 0
)

data class SaveMoodWithDateEntity(
    val score: Int,
    val date: String
)

data class MoodPresentEntity(
    val id: String,
    val score: Int,
    val moodTitleResStr: Int,
    val date: String,
)

data class MoodTrackerResultEntity(
    val id: String,
    val score: Int,
    val date: String,
)

data class MoodTrackerRespEntity(
    val id: String
)
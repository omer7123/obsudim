package com.example.mypsychologist.domain.entity.diaryEntity

import kotlinx.serialization.SerialName

data class FreeDiaryEntity (
    val id: String,
    val text: String
)
data class NewFreeDiaryEntity (
    val text: String
)

data class SaveMoodEntity(
    val score: Int,
    val freeDiaryId: String = "",
    val thinkDiaryId: String = "",
    val diaryType: Int = 0
)

data class MoodTrackerRespEntity(
    val id: String
)
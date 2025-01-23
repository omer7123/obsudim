package com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment

import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import java.util.Date

sealed class FreeDiaryTrackerMoodScreenState {
    data class Content(
        val month: Date,
        val year: String,
        val dates: List<Pair<Date, Boolean>>,
        val freeDiaries: List<FreeDiaryEntity>,
        val freeDiariesError: Boolean = false,
        val freeDiariesLoading: Boolean = false,
    ): FreeDiaryTrackerMoodScreenState()
}
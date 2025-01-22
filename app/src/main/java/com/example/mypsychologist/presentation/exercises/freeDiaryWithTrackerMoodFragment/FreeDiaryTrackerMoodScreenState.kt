package com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment

import java.util.Date

sealed class FreeDiaryTrackerMoodScreenState {
    data class Content(
        val month: Date,
        val year: String,
        val dates: List<Pair<Date, Boolean>>
    ): FreeDiaryTrackerMoodScreenState()
}

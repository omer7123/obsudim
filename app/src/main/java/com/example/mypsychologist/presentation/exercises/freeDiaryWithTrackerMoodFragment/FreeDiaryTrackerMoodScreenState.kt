package com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment

import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodPresentEntity
import java.util.Date

sealed class FreeDiaryTrackerMoodScreenState {

    data class Content(
        val month: Date,
        val year: String,
        val dates: List<Pair<Date, Boolean>>,
        val freeDiaries: List<FreeDiaryEntity>,
        val freeDiariesError: Boolean = false,
        val freeDiariesLoading: Boolean = false,
        val mood: Float = 50f,
        val moodTitleIdSource: Int,
        val moods: List<MoodPresentEntity>,
        val addNewMoodStatus: Boolean = false,
        val moodTrackerLoading: Boolean = false,
        val moodTrackerError: Boolean = false
    ): FreeDiaryTrackerMoodScreenState()

}

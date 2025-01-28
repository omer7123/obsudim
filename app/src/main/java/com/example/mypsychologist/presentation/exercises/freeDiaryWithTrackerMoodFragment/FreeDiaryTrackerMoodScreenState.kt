package com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment

import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodPresentEntity
import java.util.Date

sealed class FreeDiaryTrackerMoodScreenState {
    data class Content(
        val calendarViewState: CalendarContent,
        val freeDiaryState: FreeDiaryViewState,
        val newMoodViewState: NewMoodStatusViewState,
        val moodsViewState: MoodsTrackerViewState,
    ) : FreeDiaryTrackerMoodScreenState()

}

data class CalendarContent(
    val month: Date,
    val year: String,
    val dates: List<Pair<Date, Boolean>>,
)

sealed interface FreeDiaryViewState {
    data class Content(
        val freeDiaries: List<FreeDiaryEntity>,
    ) : FreeDiaryViewState
    data object Loading : FreeDiaryViewState
    data object Error : FreeDiaryViewState
    data object Initial : FreeDiaryViewState
}

sealed interface NewMoodStatusViewState {
    data class Content(
        val mood: Float = 50f,
        val moodTitleIdSource: Int,
        val loading: Boolean = false
    ) : NewMoodStatusViewState
    data object Hide : NewMoodStatusViewState
}

sealed interface MoodsTrackerViewState {
    data class Content(
        val moods: List<MoodPresentEntity>,
    ) : MoodsTrackerViewState
    data object Loading : MoodsTrackerViewState
    data object Error : MoodsTrackerViewState
    data object Initial : MoodsTrackerViewState
}



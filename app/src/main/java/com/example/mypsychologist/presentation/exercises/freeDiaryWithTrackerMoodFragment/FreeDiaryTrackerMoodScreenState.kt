package com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.mypsychologist.domain.entity.diaryEntity.CalendarEntity
import com.example.mypsychologist.domain.entity.diaryEntity.EmojiEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodPresentEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity
import java.util.Date


@Stable
data class CalendarContent(
    val month: Date,
    val year: String,
    val dates: List<CalendarEntity>,
)

@Immutable
sealed interface FreeDiaryViewState {
    @Stable
    data class Content(
        val freeDiaries: List<RecordExerciseEntity>,
    ) : FreeDiaryViewState
    data object Loading : FreeDiaryViewState
    data object Error : FreeDiaryViewState
    data object Initial : FreeDiaryViewState
}


sealed interface NewMoodStatusViewState {

    data class Content(
        val mood: Float = 50f,
        val moodTitleIdSource: Int,
        val loading: Boolean = false,
        val smiles: List<EmojiEntity>,
        val selectedSmiles: Set<Int>,
    ) : NewMoodStatusViewState
    data object Hide : NewMoodStatusViewState
}

@Stable
sealed interface MoodsTrackerViewState {
    @Stable
    data class Content(
        val moods: List<MoodPresentEntity>,
    ) : MoodsTrackerViewState
    data object Loading : MoodsTrackerViewState
    data object Error : MoodsTrackerViewState
    data object Initial : MoodsTrackerViewState
}



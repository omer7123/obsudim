package com.example.mypsychologist.presentation.exercises.trackerMoodFragment

sealed class TrackerMoodScreenState {
    data object Initial: TrackerMoodScreenState()
    data object SuccessResp : TrackerMoodScreenState()
    data object Loading : TrackerMoodScreenState()
    data class Content(val titleMoodResId: Int): TrackerMoodScreenState()
    data class Error(val msg: String) : TrackerMoodScreenState()
}
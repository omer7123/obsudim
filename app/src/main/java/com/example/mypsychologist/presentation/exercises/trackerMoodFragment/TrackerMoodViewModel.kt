package com.example.mypsychologist.presentation.exercises.trackerMoodFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.useCase.exerciseUseCases.MarkAsCompleteExerciseUseCase
import com.example.mypsychologist.domain.useCase.freeDiaryUseCase.SaveMoodTrackerUseCase
import com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.FreeDiaryTrackerMoodScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class TrackerMoodViewModel @Inject constructor(
    private val saveTrackMoodTrackerUseCase: SaveMoodTrackerUseCase,
    private val markAsCompleteExerciseUseCase: MarkAsCompleteExerciseUseCase
) : ViewModel() {

    private val _stateScreen: MutableLiveData<TrackerMoodScreenState> =
        MutableLiveData(TrackerMoodScreenState.Initial)
    val stateScreen: LiveData<TrackerMoodScreenState> = _stateScreen

    private val _viewState: MutableStateFlow<FreeDiaryTrackerMoodScreenState> = MutableStateFlow(
        FreeDiaryTrackerMoodScreenState.Content(
            month = Calendar.getInstance().time,
            year = Calendar.getInstance().get(Calendar.YEAR).toString(),
            dates = getDatesList()
        )
    )

    val viewState: StateFlow<FreeDiaryTrackerMoodScreenState> = _viewState

    fun nextMonth() {
        val currentMonth = (_viewState.value as FreeDiaryTrackerMoodScreenState.Content).month
        val nextMonth = Calendar.getInstance().apply {
            time = currentMonth
            add(Calendar.MONTH, 1)
        }

        val nextYear = nextMonth.get(Calendar.YEAR).toString()

        _viewState.value = (_viewState.value as FreeDiaryTrackerMoodScreenState.Content).copy(
            month = nextMonth.time,
            year = nextYear,
            dates = getDatesList(nextMonth.time)
        )
    }

    fun prevMonth(){
        val currentMonth = (viewState.value as FreeDiaryTrackerMoodScreenState.Content).month
        val prevMonth = Calendar.getInstance().apply {
            time = currentMonth
            add(Calendar.MONTH, -1)
        }
        val year = prevMonth.get(Calendar.YEAR).toString()

        _viewState.value = (viewState.value as FreeDiaryTrackerMoodScreenState.Content).copy(
            month = prevMonth.time,
            year = year,
            getDatesList(prevMonth.time)
        )
    }

    fun onClickDate(selectedDate: Date) {
        val currentViewState = _viewState.value as FreeDiaryTrackerMoodScreenState.Content

        val updatedDates = currentViewState.dates.map {
            when {
                it.first == selectedDate -> it.first to true
                it.first != selectedDate -> it.first to false
                else -> it
            }
        }

        _viewState.value = currentViewState.copy(dates = updatedDates)
    }

    fun saveMood(score: Int, taskId: String = "") {
        _stateScreen.value = TrackerMoodScreenState.Loading
        viewModelScope.launch {
            when (val res = saveTrackMoodTrackerUseCase(SaveMoodEntity(score))) {
                is Resource.Error -> _stateScreen.value =
                    TrackerMoodScreenState.Error(res.msg.toString())

                Resource.Loading -> _stateScreen.value = TrackerMoodScreenState.Loading
                is Resource.Success -> {
                    if (taskId.isEmpty()) _stateScreen.value = TrackerMoodScreenState.SuccessResp
                    else markAsCompleteExerciseUseCase(DailyTaskMarkIdEntity(taskId)).collect {
                        when (it) {
                            is Resource.Error -> {}
                            Resource.Loading -> {}
                            is Resource.Success -> _stateScreen.value =
                                TrackerMoodScreenState.SuccessResp
                        }
                    }
                }
            }
        }
    }

    private fun getDatesList(currentMonth: Date = Calendar.getInstance().time): List<Pair<Date, Boolean>> {
        val calendar = Calendar.getInstance().apply { time = currentMonth }
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        return (1..daysInMonth).map {
            calendar.set(Calendar.DAY_OF_MONTH, it)
            if (currentDay == it) calendar.time to true
            else calendar.time to false
        }
    }
}
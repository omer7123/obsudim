package com.example.mypsychologist.presentation.exercises.trackerMoodFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodPresentEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodWithDateEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.useCase.exerciseUseCases.MarkAsCompleteExerciseUseCase
import com.example.mypsychologist.domain.useCase.freeDiaryUseCase.GetAllMoodTrackersUseCase
import com.example.mypsychologist.domain.useCase.freeDiaryUseCase.GetFreeDiariesByDayUseCase
import com.example.mypsychologist.domain.useCase.freeDiaryUseCase.SaveMoodTrackerUseCase
import com.example.mypsychologist.domain.useCase.freeDiaryUseCase.SaveMoodTrackerWithDateUseCase
import com.example.mypsychologist.extensions.convertDateToString
import com.example.mypsychologist.extensions.convertToISO8601
import com.example.mypsychologist.extensions.convertToTimeOnly
import com.example.mypsychologist.extensions.isSameDay
import com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.FreeDiaryTrackerMoodScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class TrackerMoodViewModel @Inject constructor(
    private val saveTrackMoodTrackerUseCase: SaveMoodTrackerUseCase,
    private val markAsCompleteExerciseUseCase: MarkAsCompleteExerciseUseCase,
    private val getFreeDiariesByDayUseCase: GetFreeDiariesByDayUseCase,
    private val saveMoodTrackerWithDateUseCase: SaveMoodTrackerWithDateUseCase,
    private val getAllMoodTrackersUseCase: GetAllMoodTrackersUseCase
) : ViewModel() {

    private val _stateScreen: MutableLiveData<TrackerMoodScreenState> =
        MutableLiveData(TrackerMoodScreenState.Initial)
    val stateScreen: LiveData<TrackerMoodScreenState> = _stateScreen

    private val _viewState: MutableStateFlow<FreeDiaryTrackerMoodScreenState> = MutableStateFlow(
        FreeDiaryTrackerMoodScreenState.Content(
            month = Calendar.getInstance().time,
            year = Calendar.getInstance().get(Calendar.YEAR).toString(),
            dates = getDatesList(),
            freeDiaries = emptyList(),
            moodTitleIdSource = R.string.normal_mood,
            moods = emptyList()
        )
    )

    val viewState: StateFlow<FreeDiaryTrackerMoodScreenState> = _viewState

    init {
        viewModelScope.launch {
            getNotes(Date())
            getMoodTrackers(Date())
        }
    }


    fun saveMoodTrack() {
        val currentViewState = viewState.value as FreeDiaryTrackerMoodScreenState.Content

        val date = Date()
        val selectedDate = currentViewState.dates.find {
            it.second
        }!!.first

        val isSameDay = date isSameDay selectedDate

        if (!isSameDay) {
            viewModelScope.launch {
                _viewState.value = currentViewState.copy(moodTrackerLoading = true)
                saveMoodTrackerWithDateUseCase(
                    SaveMoodWithDateEntity(
                        score = currentViewState.mood.toInt(),
                        date = selectedDate.convertDateToString()
                    )
                ).collect { resource ->
                    when (resource) {
                        is Resource.Error -> {}
                        Resource.Loading -> _viewState.value =
                            currentViewState.copy(moodTrackerLoading = true)

                        is Resource.Success -> {
                            _viewState.value =
                                currentViewState.copy(moodTrackerLoading = false)
                            getMoodTrackers(selectedDate)
                        }
                    }
                }
            }
        } else {
            viewModelScope.launch {
                when (saveTrackMoodTrackerUseCase(SaveMoodEntity(currentViewState.mood.toInt()))) {
                    is Resource.Error -> {}

                    Resource.Loading -> _viewState.value =
                        currentViewState.copy(moodTrackerLoading = true)

                    is Resource.Success -> {
                        _viewState.value = currentViewState.copy(moodTrackerLoading = false)
                        getMoodTrackers(selectedDate)
                    }
                }
            }
        }
    }

    fun nextMonth() {
        val currentViewState = viewState.value as FreeDiaryTrackerMoodScreenState.Content

        val currentMonth = currentViewState.month
        val nextMonth = Calendar.getInstance().apply {
            time = currentMonth
            add(Calendar.MONTH, 1)
        }

        val nextYear = nextMonth.get(Calendar.YEAR).toString()
        val newDateList = getDatesList(nextMonth.time)

        _viewState.value = currentViewState.copy(
            month = nextMonth.time, year = nextYear, dates = newDateList
        )

        val currentDateAfterNext = newDateList.find {
            it.second
        }
        viewModelScope.launch {
            getNotes(currentDateAfterNext!!.first)
        }
    }

    //TODO("Надо сделать чтобы при свайпе месяца выбранный день сохранялся")
    fun prevMonth() {
        val currentViewState = viewState.value as FreeDiaryTrackerMoodScreenState.Content

        val currentMonth = currentViewState.month
        val prevMonth = Calendar.getInstance().apply {
            time = currentMonth
            add(Calendar.MONTH, -1)
        }
        val year = prevMonth.get(Calendar.YEAR).toString()

        val newDateList = getDatesList(prevMonth.time)

        _viewState.value = currentViewState.copy(
            month = prevMonth.time, year = year, dates = newDateList
        )

        val currentDateAfterPrev = newDateList.find {
            it.second
        }
        viewModelScope.launch {
            getNotes(currentDateAfterPrev!!.first)
        }
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

        viewModelScope.launch {
            getMoodTrackers(selectedDate)
            getNotes(selectedDate)
        }
    }

    private suspend fun getNotes(selectedDate: Date) {
        val currentViewState = _viewState.value as FreeDiaryTrackerMoodScreenState.Content
        _viewState.value = currentViewState.copy(
            freeDiaries = emptyList(), freeDiariesLoading = true, freeDiariesError = false
        )

        getFreeDiariesByDayUseCase(selectedDate.convertDateToString()).collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    _viewState.value = currentViewState.copy(
                        freeDiaries = emptyList(), freeDiariesError = true
                    )
                }

                Resource.Loading -> _viewState.value = currentViewState.copy(
                    freeDiaries = emptyList(), freeDiariesLoading = true, freeDiariesError = false
                )

                is Resource.Success -> {
                    _viewState.value = currentViewState.copy(
                        freeDiaries = resource.data.map {
                            FreeDiaryEntity(
                                it.id, it.text, it.createdAt.convertToTimeOnly()
                            )
                        }, freeDiariesLoading = false, freeDiariesError = false
                    )
                }
            }

        }
    }

    private suspend fun getMoodTrackers(selectedDate: Date) {
        val currentViewState = _viewState.value as FreeDiaryTrackerMoodScreenState.Content
        _viewState.value = currentViewState.copy(
            moods = emptyList(), moodTrackerLoading = true, moodTrackerError = false
        )

        getAllMoodTrackersUseCase(selectedDate.convertToISO8601()).collect { resource ->
            when (resource) {
                is Resource.Error -> _viewState.value = currentViewState.copy(
                    moods = emptyList(), moodTrackerLoading = false, moodTrackerError = true
                )

                Resource.Loading -> _viewState.value = currentViewState.copy(
                    moods = emptyList(), moodTrackerLoading = true, moodTrackerError = false
                )

                is Resource.Success ->{

                    _viewState.value = currentViewState.copy(
                        moods = resource.data.map {
                            MoodPresentEntity(
                                id = it.id,
                                score = it.score,
                                moodTitleResStr = calculateMoodTitle(it.score),
                                date = it.date.convertToTimeOnly()
                            )
                        },
                        moodTrackerLoading = false,
                        moodTrackerError = false,
                        addNewMoodStatus = resource.data.isEmpty()
                    )
                }

            }
        }
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

    fun getSelectedDay(): String? {
        val currentViewState = viewState.value as FreeDiaryTrackerMoodScreenState.Content
        val selectedDate = currentViewState.dates.find {
            it.second
        }!!.first
        val date = Date()

        return if (date isSameDay selectedDate) null
        else selectedDate.convertDateToString()
    }

    fun reInitData() {
        val currentViewState = viewState.value as FreeDiaryTrackerMoodScreenState.Content
        val selectedDate = currentViewState.dates.find {
            it.second
        }!!.first
        viewModelScope.launch {
            getNotes(selectedDate)
        }
    }

    fun changeMood(newMood: Float) {
        val newTitleMood = calculateMoodTitle(newMood.toInt())
        _viewState.value = (viewState.value as FreeDiaryTrackerMoodScreenState.Content).copy(
            mood = newMood, moodTitleIdSource = newTitleMood
        )
    }

    private fun calculateMoodTitle(mood: Int): Int {
        return when (mood) {
            in 0..20 -> R.string.terrible_mood
            in 21..40 -> R.string.bad_mood
            in 41..59 -> R.string.normal_mood
            in 60..79 -> R.string.good_mood
            in 80..100 -> R.string.super_mood
            else -> {
                R.string.super_mood
            }
        }
    }

    fun changeStatusAddNewMood(newStatus: Boolean) {
        _viewState.value =
            (_viewState.value as FreeDiaryTrackerMoodScreenState.Content).copy(addNewMoodStatus = newStatus)
    }
}
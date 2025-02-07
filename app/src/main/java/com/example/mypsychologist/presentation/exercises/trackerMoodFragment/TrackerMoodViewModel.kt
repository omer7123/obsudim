package com.example.mypsychologist.presentation.exercises.trackerMoodFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.CalendarEntity
import com.example.mypsychologist.domain.entity.diaryEntity.CalendarResponseEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodPresentEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodWithDateEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity
import com.example.mypsychologist.domain.useCase.exerciseUseCases.MarkAsCompleteExerciseUseCase
import com.example.mypsychologist.domain.useCase.freeDiaryUseCase.GetAllMoodTrackersUseCase
import com.example.mypsychologist.domain.useCase.freeDiaryUseCase.GetDatesWithDiariesUseCase
import com.example.mypsychologist.domain.useCase.freeDiaryUseCase.GetFreeDiariesByDayUseCase
import com.example.mypsychologist.domain.useCase.freeDiaryUseCase.SaveMoodTrackerUseCase
import com.example.mypsychologist.domain.useCase.freeDiaryUseCase.SaveMoodTrackerWithDateUseCase
import com.example.mypsychologist.extensions.convertDateToString
import com.example.mypsychologist.extensions.convertLondonTimeToDeviceTime
import com.example.mypsychologist.extensions.convertToISO8601
import com.example.mypsychologist.extensions.isSameDay
import com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.CalendarContent
import com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.FreeDiaryViewState
import com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.MoodsTrackerViewState
import com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.NewMoodStatusViewState
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
    private val getAllMoodTrackersUseCase: GetAllMoodTrackersUseCase,
    private val getDatesWithDiariesUseCase: GetDatesWithDiariesUseCase
) : ViewModel() {

    private val _stateScreen: MutableLiveData<TrackerMoodScreenState> =
        MutableLiveData(TrackerMoodScreenState.Initial)
    val stateScreen: LiveData<TrackerMoodScreenState> = _stateScreen

    private val _calendarViewState: MutableStateFlow<CalendarContent> = MutableStateFlow(
        CalendarContent(
                month = Calendar.getInstance().time,
                year = Calendar.getInstance().get(Calendar.YEAR).toString(),
                dates = collectDates(Calendar.getInstance().time),
        )
    )

    val calendarViewState: StateFlow<CalendarContent> = _calendarViewState

    private val _freeDiaryViewState: MutableStateFlow<FreeDiaryViewState> = MutableStateFlow(FreeDiaryViewState.Initial)
    val freeDiaryViewState: StateFlow<FreeDiaryViewState> = _freeDiaryViewState

    private val _newMoodViewState: MutableStateFlow<NewMoodStatusViewState> = MutableStateFlow(NewMoodStatusViewState.Hide)
    val newMoodViewState: StateFlow<NewMoodStatusViewState> = _newMoodViewState

    private val _moodsViewState: MutableStateFlow<MoodsTrackerViewState> = MutableStateFlow(MoodsTrackerViewState.Initial)
    val moodsViewState: StateFlow<MoodsTrackerViewState> = _moodsViewState

    init {
        _freeDiaryViewState.value = FreeDiaryViewState.Loading
        _moodsViewState.value = MoodsTrackerViewState.Loading

        getDatesList()
        getNotes(Date())
        getMoodTrackers(Date())
    }


    fun saveMoodTrack() {
        val date = Date()
        val selectedDate = calendarViewState.value.dates.find {
            it.signal
        }!!.date

        val isSameDay = date isSameDay selectedDate

        val currentNewMoodViewState = (_newMoodViewState.value as NewMoodStatusViewState.Content)
        _newMoodViewState.value = currentNewMoodViewState.copy(
            loading = true
        )

        if (!isSameDay) {
            viewModelScope.launch {


                saveMoodTrackerWithDateUseCase(
                    SaveMoodWithDateEntity(
                        score = currentNewMoodViewState.mood.toInt(),
                        date = selectedDate.convertDateToString()
                    )
                ).collect { resource ->
                    when (resource) {
                        is Resource.Error -> {}
                        Resource.Loading -> _newMoodViewState.value = currentNewMoodViewState.copy(loading = true)

                        is Resource.Success -> {
                            _newMoodViewState.value = currentNewMoodViewState.copy(loading = false)
                            getMoodTrackers(selectedDate)
                        }
                    }
                }
            }
        } else {
            viewModelScope.launch {
                when (saveTrackMoodTrackerUseCase(
                    SaveMoodEntity(
                        currentNewMoodViewState.mood.toInt()
                    )
                )) {
                    is Resource.Error -> {}
                    Resource.Loading -> _newMoodViewState.value = currentNewMoodViewState.copy(loading = true)
                    is Resource.Success -> {
                        _newMoodViewState.value = currentNewMoodViewState.copy(loading = true)
                        getMoodTrackers(selectedDate)
                    }
                }
            }
        }
    }

    fun nextMonth() = changeMonth(1)
    //TODO("Надо сделать чтобы при свайпе месяца выбранный день сохранялся")
    fun prevMonth() = changeMonth(-1)

    private fun changeMonth(amount: Int){

        val currentMonth = calendarViewState.value.month
        val prevMonth = Calendar.getInstance().apply {
            time = currentMonth
            add(Calendar.MONTH, amount)
        }
        val year = prevMonth.get(Calendar.YEAR).toString()


        _calendarViewState.value = calendarViewState.value.copy(
            month = prevMonth.time, year = year, dates = collectDates(prevMonth.time)
        )
        getDatesList(prevMonth.time)

        val currentDateAfterPrev = calendarViewState.value.dates.find {
            it.signal
        }

        _freeDiaryViewState.value = FreeDiaryViewState.Loading
        _moodsViewState.value = MoodsTrackerViewState.Loading

        getNotes(currentDateAfterPrev!!.date)
        getMoodTrackers(currentDateAfterPrev.date)
    }

    fun onClickDate(selectedDate: Date) {
        val updatedDates = calendarViewState.value.dates.map {
            when {
                it.date == selectedDate -> it.copy(signal = true)
                it.date != selectedDate -> it.copy(signal = false)
                else -> it
            }
        }

        _calendarViewState.value =
            _calendarViewState.value.copy(dates = updatedDates)

        _freeDiaryViewState.value = FreeDiaryViewState.Loading
        _moodsViewState.value = MoodsTrackerViewState.Loading

        getNotes(selectedDate)
        getMoodTrackers(selectedDate)
    }

    private fun getNotes(selectedDate: Date) {
        viewModelScope.launch {
            getFreeDiariesByDayUseCase(selectedDate.convertDateToString()).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _freeDiaryViewState.value = FreeDiaryViewState.Error
                    }

                    Resource.Loading -> {
                        _freeDiaryViewState.value = FreeDiaryViewState.Loading
                    }

                    is Resource.Success -> {
                        _freeDiaryViewState.value = FreeDiaryViewState.Content(
                            freeDiaries = resource.data.map {
                                RecordExerciseEntity(
                                    it.id, it.text, it.createdAt.convertLondonTimeToDeviceTime()
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    private fun getMoodTrackers(selectedDate: Date) {
        viewModelScope.launch {
            getAllMoodTrackersUseCase(selectedDate.convertToISO8601()).collect { resource ->
                when (resource) {
                    is Resource.Error -> _moodsViewState.value = MoodsTrackerViewState.Error

                    Resource.Loading -> _moodsViewState.value = MoodsTrackerViewState.Loading

                    is Resource.Success -> {
                        _moodsViewState.value = MoodsTrackerViewState.Content(
                            moods = resource.data.map {
                                MoodPresentEntity(
                                    id = it.id,
                                    score = it.score,
                                    moodTitleResStr = calculateMoodTitle(it.score),
                                    date = it.date.convertLondonTimeToDeviceTime()
                                )
                            }
                        )
                        if (resource.data.isNotEmpty()) changeStatusAddNewMood(false)
                        else changeStatusAddNewMood(true)
                    }
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

    private fun getDatesList(currentMonth: Date = Calendar.getInstance().time) {
        viewModelScope.launch {
            getDatesWithDiariesUseCase(currentMonth.time.toString().take(10).toInt()).collect{resource->
                when(resource){
                    is Resource.Error -> Unit
                    Resource.Loading -> Unit
                    is Resource.Success -> {
                        _calendarViewState.value =
                            calendarViewState.value.copy(dates = collectDates(currentMonth, resource.data))
                    }
                }
            }
        }
    }

    private fun collectDates(currentMonth: Date, datesWithDiary: List<CalendarResponseEntity> = emptyList()): List<CalendarEntity>{
        val calendar = Calendar.getInstance().apply { time = currentMonth }
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        return (1..daysInMonth).map {
            calendar.set(Calendar.DAY_OF_MONTH, it)
            var signal = false
            if (currentDay == it) signal = true
            if (datesWithDiary.isEmpty()) {
                CalendarEntity(calendar.time, signal, false)
            }else {
                CalendarEntity(calendar.time, signal, datesWithDiary[it - 1].diary)
            }
        }
    }

    fun getSelectedDay(): String? {
        val selectedDate = calendarViewState.value.dates.find {
            it.signal
        }!!.date
        val date = Date()

        return if (date isSameDay selectedDate) null
        else selectedDate.convertDateToString()
    }

    fun reInitData() {
        val selectedDate = calendarViewState.value.dates.find {
            it.signal
        }!!.date
        viewModelScope.launch {
            getNotes(selectedDate)
        }
    }

    fun changeMood(newMood: Float) {
        val newTitleMood = calculateMoodTitle(newMood.toInt())

        _newMoodViewState.value = (_newMoodViewState.value as NewMoodStatusViewState.Content).copy(
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
        when (newStatus) {
            true -> {
                _newMoodViewState.value = NewMoodStatusViewState.Content(moodTitleIdSource = R.string.normal_mood)
            }
            false -> {
                _newMoodViewState.value = NewMoodStatusViewState.Hide
            }
        }
    }
}
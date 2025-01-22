package com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.exercises.trackerMoodFragment.TrackerMoodViewModel
import com.example.mypsychologist.ui.core.CalendarView
import com.example.mypsychologist.ui.theme.AppTheme
import java.util.Date
import javax.inject.Inject


class FreeDiaryTrackerMoodFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: TrackerMoodViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TrackerMoodViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                FreeDiaryTrackerMoodScreen(viewModel = viewModel)
            }
        }
    }

    @Composable
    fun FreeDiaryTrackerMoodScreen(viewModel: TrackerMoodViewModel) {
        val viewState = viewModel.viewState.collectAsState()
        when (val res = viewState.value) {
            is FreeDiaryTrackerMoodScreenState.Content -> {
                FreeDiaryTrackerMoodScreenContent(
                    res = res,
                    onClickNext = {viewModel.nextMonth()},
                    onClickPrev = {viewModel.prevMonth()},
                    onClickDate = {selectedDate ->
                        viewModel.onClickDate(selectedDate)
                    }
                )
            }
        }
    }

    @Composable
    fun FreeDiaryTrackerMoodScreenContent(
        res: FreeDiaryTrackerMoodScreenState.Content,
        onClickNext: () -> Unit,
        onClickPrev: () -> Unit,
        onClickDate: (Date) -> Unit,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CalendarView(
                month = res.month,
                years = res.year,
                date = res.dates,
                displayNext = true,
                displayPrev = true,
                onClickNext = {
                    onClickNext()
                },
                onClickPrev = {
                    onClickPrev()
                },
                onClick = { selectedDate ->
                    onClickDate(selectedDate)
                },
                startFromSunday = false,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
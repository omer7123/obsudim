package com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.exercises.trackerMoodFragment.TrackerMoodViewModel
import com.example.mypsychologist.ui.core.CalendarView
import com.example.mypsychologist.ui.core.PrimaryTextButton
import com.example.mypsychologist.ui.core.formatToDayString
import com.example.mypsychologist.ui.core.formatToMonthStringInf
import com.example.mypsychologist.ui.exercises.cbt.NewFreeDiaryFragment
import com.example.mypsychologist.ui.theme.AppTheme
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


class FreeDiaryTrackerMoodFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: TrackerMoodViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TrackerMoodViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        viewModel.reInitData()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    FreeDiaryTrackerMoodScreen(viewModel = viewModel, findNavController())
                }
            }
        }
    }

    @Composable
    fun FreeDiaryTrackerMoodScreen(
        viewModel: TrackerMoodViewModel,
        navController: NavController
    ) {
        val viewState = viewModel.viewState.collectAsState()
        when (val res = viewState.value) {
            is FreeDiaryTrackerMoodScreenState.Content -> {
                FreeDiaryTrackerMoodScreenContent(res = res,
                    onClickNext = { viewModel.nextMonth() },
                    onClickPrev = { viewModel.prevMonth() },
                    onClickDate = { selectedDate ->
                        viewModel.onClickDate(selectedDate)
                    },
                    writeNoteClick = {
                        navController.navigate(
                            resId = R.id.action_freeDiaryTrackerMoodFragment_to_newFreeDiaryFragment,
                            args = bundleOf(NewFreeDiaryFragment.DATE to viewModel.getSelectedDay())
                        )
                    },
                    onNavIconClick = {navController.popBackStack()}
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
        writeNoteClick: () -> Unit,
        onNavIconClick: () -> Unit
    ) {

        ScaffoldWithBottomSheetExample(
            res = res,
            onClickNext = onClickNext,
            onClickPrev = onClickPrev,
            onClickDate = onClickDate,
            writeNoteClick = writeNoteClick,
            onNavIconClick = onNavIconClick
        )
    }

    @Composable
    fun ScaffoldWithBottomSheetExample(
        res: FreeDiaryTrackerMoodScreenState.Content,
        onClickNext: () -> Unit,
        onClickPrev: () -> Unit,
        onClickDate: (Date) -> Unit,
        writeNoteClick: () -> Unit,
        onNavIconClick: () -> Unit
    ) {
        androidx.compose.material.BottomSheetScaffold(sheetShape = RoundedCornerShape(
            topStart = 28.dp, topEnd = 28.dp
        ),

            sheetContent = {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.notes),
                        style = AppTheme.typography.titleXS,
                        color = AppTheme.colors.primaryText
                    )
                    when{
                        res.freeDiariesLoading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 20.dp),
                            )
                        }
                        res.freeDiariesError -> {
                            Text(
                                text = stringResource(id = R.string.unknown_error_placeholder),
                                style = AppTheme.typography.bodyL,
                                color = AppTheme.colors.primaryText,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                        res.freeDiaries.isNotEmpty() -> {
                            LazyColumn(
                                contentPadding = PaddingValues(top = 20.dp),
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                items(res.freeDiaries){record->
                                    RecordItem(item = record)
                                }
                            }
                        }
                        else -> {
                            Text(
                                text = stringResource(id = R.string.diary_empty_placeholder),
                                style = AppTheme.typography.bodyL,
                                color = AppTheme.colors.primaryText,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                    }

                    PrimaryTextButton(
                        textString = stringResource(id = R.string.write_note),
                        onClick = {writeNoteClick()},
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
            },

            sheetPeekHeight = 400.dp, content = {
                Column(
                    modifier = Modifier.background(AppTheme.colors.primaryBackground)
                ) {
                    Row(
                        modifier = Modifier
                            .statusBarsPadding()
                            .height(64.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        IconButton(
                            onClick = {
                                onNavIconClick()
                            },
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back_white),
                                contentDescription = stringResource(id = R.string.feedback),
                                tint = AppTheme.colors.tertiaryBackground,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        val timeStr =
                            Date().formatToDayString() + " " + Date().formatToMonthStringInf()
                        Text(
                            text = timeStr,
                            style = AppTheme.typography.titleCygreFont,
                            color = AppTheme.colors.primaryTextInvert,
                        )
                    }


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
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 26.dp)
                    )
                }
            })
    }


    @Composable
    fun RecordItem(
        item: FreeDiaryEntity, modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = AppTheme.colors.tertiaryBackground, shape = RoundedCornerShape(12.dp)
                )

        ) {
            Text(
                text = item.text,
                style = AppTheme.typography.bodyMBold,
                color = AppTheme.colors.primaryText,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            Text(
                text = item.createdAt,
                style = AppTheme.typography.bodyM,
                color = AppTheme.colors.primaryText,
                modifier = Modifier.padding(start = 16.dp, top = 6.dp, bottom = 16.dp)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun RecordItem_Preview() {
        AppTheme {
            RecordItem(
                item = FreeDiaryEntity(
                    "dsds",
                    "Заметка раз-раз-раз",
                    "9:30"
                ),
                modifier = Modifier.padding(all = 15.dp)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ScaffoldWithBottomSheetExample_Preview() {
        val calendar = Calendar.getInstance()
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dates = (1..daysInMonth).map {
            calendar.set(Calendar.DAY_OF_MONTH, it)
            Pair(calendar.time, it % 5 == 0) // Пример: сигналы для каждых 5 дней
        }
        AppTheme {
            ScaffoldWithBottomSheetExample(
                res = FreeDiaryTrackerMoodScreenState.Content(
                    month = Date(),
                    year = "2024",
                    dates = dates,
                    freeDiaries = listOf<FreeDiaryEntity>(
                        FreeDiaryEntity("ds", "Заметка 1", "9:30"),
                        FreeDiaryEntity("ds", "Заметка 2", "9:30"),
                    ),
                    freeDiariesLoading = true
                ),
                onClickNext = {},
                onClickPrev = {},
                onClickDate = {},
                writeNoteClick = {},
                onNavIconClick = {},
            )
        }
    }
}
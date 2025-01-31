    package com.example.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment

    import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.heightIn
    import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import com.example.mypsychologist.domain.entity.diaryEntity.MoodPresentEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.exercises.trackerMoodFragment.TrackerMoodViewModel
import com.example.mypsychologist.ui.core.CalendarView
import com.example.mypsychologist.ui.core.PrimaryTextButton
import com.example.mypsychologist.ui.core.formatToDayString
import com.example.mypsychologist.ui.core.formatToMonthStringInf
import com.example.mypsychologist.ui.exercises.cbt.NewFreeDiaryFragment
import com.example.mypsychologist.ui.theme.AppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
            viewModel: TrackerMoodViewModel, navController: NavController
        ) {
            val viewState = viewModel.viewState.collectAsState()

            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(color = AppTheme.colors.primaryBackground)
            when (viewState.value) {
                is FreeDiaryTrackerMoodScreenState.Content -> {
                    FreeDiaryTrackerMoodScreenContent(res = viewState.value as FreeDiaryTrackerMoodScreenState.Content,
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
                        onNavIconClick = { navController.popBackStack() },
                        onIconAddClick = { viewModel.changeStatusAddNewMood(it) },
                        onClickSaveMood = { viewModel.saveMoodTrack() })
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
            onNavIconClick: () -> Unit,
            onIconAddClick: (Boolean) -> Unit,
            onClickSaveMood: () -> Unit,
        ) {
            androidx.compose.material.BottomSheetScaffold(sheetShape = RoundedCornerShape(
                topStart = 28.dp, topEnd = 28.dp,
            ),
                modifier = Modifier.safeDrawingPadding(),
                sheetContent = {
                SheetContent(
                    res, writeNoteClick, onIconAddClick, onClickSaveMood
                )
            }, sheetPeekHeight = 400.dp, content = {
                MainContent(
                    res = res.calendarViewState,
                    onClickNext = onClickNext,
                    onClickPrev = onClickPrev,
                    onClickDate = onClickDate,
                    onNavIconClick = onNavIconClick,
                    )
                })
        }

        @Composable
        fun MainContent(
            res: CalendarContent,
            onClickNext: () -> Unit,
            onClickPrev: () -> Unit,
            onClickDate: (Date) -> Unit,
            onNavIconClick: () -> Unit
        ) {
            Column(
                modifier = Modifier.background(AppTheme.colors.primaryBackground)
            ) {
                Row(
                    modifier = Modifier

                        .height(64.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    IconButton(
                        onClick = {
                            onNavIconClick()
                        }, modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back_white),
                            contentDescription = stringResource(id = R.string.feedback),
                            tint = AppTheme.colors.tertiaryBackground,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    val timeStr = Date().formatToDayString() + " " + Date().formatToMonthStringInf()

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
        }

        @Composable
        fun SheetContent(
            res: FreeDiaryTrackerMoodScreenState.Content,
            writeNoteClick: () -> Unit,
            onIconAddClick: (Boolean) -> Unit,
            onClickSaveMood: () -> Unit
        ) {
            Column(modifier = Modifier
                .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.notes),
                    style = AppTheme.typography.titleXS,
                    color = AppTheme.colors.primaryText,
                )

                FreeDiaryContent(
                    res.freeDiaryState,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                PrimaryTextButton(
                    textString = stringResource(id = R.string.write_note),
                    onClick = { writeNoteClick() },
                    modifier = Modifier.padding(top = 20.dp)
                )
                
                AddNewMoodContent(
                    newMoodViewState = res.newMoodViewState,
                    onClickSaveMood = {onClickSaveMood()},
                    onIconAddClick = {onIconAddClick(it)},
                    modifier = Modifier.padding(top = 40.dp),
                )
                val contentPaddingTopForMoods =
                    if (res.newMoodViewState is NewMoodStatusViewState.Content) 40
                    else 20

                MoodsContent(res.moodsViewState, modifier = Modifier.padding(top = contentPaddingTopForMoods.dp))
            }
        }

        @Composable
        fun MoodsContent(moodsViewState: MoodsTrackerViewState, modifier: Modifier) {
            when (moodsViewState) {
                is MoodsTrackerViewState.Content -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = modifier
                    ) {
                        items(moodsViewState.moods) { item ->
                            RecordMood(item)
                        }
                    }
                }

                MoodsTrackerViewState.Error -> {
                    Text(
                        text = stringResource(id = R.string.unknown_error_placeholder),
                        style = AppTheme.typography.bodyL,
                        color = AppTheme.colors.primaryText,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }

                MoodsTrackerViewState.Initial -> Unit

                MoodsTrackerViewState.Loading -> {
                    Box(Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(top = 20.dp),
                        )
                    }
                }
            }
        }

        @Composable
        fun AddNewMoodContent(
            newMoodViewState: NewMoodStatusViewState,
            onClickSaveMood: () -> Unit,
            onIconAddClick: (Boolean) -> Unit,
            modifier: Modifier = Modifier
        ) {
            when (newMoodViewState) {
                is NewMoodStatusViewState.Content -> {
                    Column(modifier = Modifier) {
                        Text(
                            text = stringResource(id = R.string.mood),
                            style = AppTheme.typography.titleXS,
                            color = AppTheme.colors.primaryText,
                            modifier = modifier
                        )

                        Slider(
                            value = newMoodViewState.mood,
                            onValueChange = { viewModel.changeMood(it) },
                            colors = SliderDefaults.colors(
                                thumbColor = AppTheme.colors.primaryBackground,
                                activeTrackColor = AppTheme.colors.primaryBackground,
                                inactiveTrackColor = AppTheme.colors.secondaryBackground,
                            ),
                            steps = 0,
                            valueRange = 0f..100f,
                            modifier = Modifier.padding(top = 20.dp)
                        )

                        Text(
                            text = stringResource(id = newMoodViewState.moodTitleIdSource),
                            style = AppTheme.typography.bodyS,
                            color = AppTheme.colors.primaryBackground,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .background(
                                    color = AppTheme.colors.secondaryBackground,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        PrimaryTextButton(
                            textString = stringResource(id = R.string.save),
                            onClick = { onClickSaveMood() },
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                }
                NewMoodStatusViewState.Hide -> {
                    Row(
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.mood),
                            style = AppTheme.typography.titleXS,
                            color = AppTheme.colors.primaryText,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(painter = painterResource(id = R.drawable.ic_add_circle),
                            contentDescription = stringResource(
                                id = R.string.add_note_tracker
                            ),
                            tint = AppTheme.colors.primaryBackground,
                            modifier = Modifier.clickable {
                                onIconAddClick(true)
                            }
                        )
                    }
                }
            }
        }

        @Composable
        fun FreeDiaryContent(
            freeDiaryState: FreeDiaryViewState,
            modifier: Modifier = Modifier
        ) {
            when (freeDiaryState) {
                is FreeDiaryViewState.Content -> {
                    if (freeDiaryState.freeDiaries.isNotEmpty()) {
                        LazyColumn(
                            contentPadding = PaddingValues(top = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier.heightIn(max = 300.dp)
                        ) {
                            items(freeDiaryState.freeDiaries) { record ->
                                RecordItem(item = record)
                            }
                        }
                    } else {
                        Text(
                            text = stringResource(id = R.string.diary_empty_placeholder),
                            style = AppTheme.typography.bodyL,
                            color = AppTheme.colors.primaryText,
                            modifier = modifier.padding(top = 10.dp)
                        )
                    }
                }

                FreeDiaryViewState.Error -> {
                    Text(
                        text = stringResource(id = R.string.unknown_error_placeholder),
                        style = AppTheme.typography.bodyL,
                        color = AppTheme.colors.primaryText,
                        modifier = modifier.padding(top = 10.dp)
                    )
                }

                FreeDiaryViewState.Initial -> Unit
                FreeDiaryViewState.Loading -> {
                    Box(Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(top = 20.dp),
                        )
                    }
                }
            }
        }

        @Composable
        fun RecordMood(
            item: MoodPresentEntity, modifier: Modifier = Modifier
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.fourthBackground, shape = RoundedCornerShape(12.dp))
            ) {

                Text(
                    text = stringResource(id = item.moodTitleResStr),
                    style = AppTheme.typography.titleCygreFont,
                    color = AppTheme.colors.primaryBackground,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 30.dp)
                )
                Text(
                    text = item.date,
                    style = AppTheme.typography.bodyS,
                    color = AppTheme.colors.primaryBackground,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(6.dp)
                        .background(
                            color = AppTheme.colors.screenBackground,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(vertical = 3.dp, horizontal = 8.dp)
                )
            }
        }

        @Composable
        fun RecordItem(
            item: FreeDiaryEntity, modifier: Modifier = Modifier
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = AppTheme.colors.tertiaryBackground,
                        shape = RoundedCornerShape(12.dp)
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
        fun ScaffoldWithBottomSheetExample_Preview() {
            val calendar = Calendar.getInstance()
            val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val dates = (1..daysInMonth).map {
                calendar.set(Calendar.DAY_OF_MONTH, it)
                Pair(calendar.time, it % 5 == 0) // Пример: сигналы для каждых 5 дней
            }
            AppTheme {
                FreeDiaryTrackerMoodScreenContent(res = FreeDiaryTrackerMoodScreenState.Content(
                    calendarViewState = CalendarContent(
                        month = Date(),
                        year = "2024",
                        dates = dates,
                    ),
                    freeDiaryState = FreeDiaryViewState.Content(
                        freeDiaries = listOf(
                            FreeDiaryEntity("ds", "Заметка 2", "9:30"),
                        ),
                    ),
                    newMoodViewState = NewMoodStatusViewState.Content(
                        moodTitleIdSource = R.string.normal_mood,
                    ),
                    moodsViewState = MoodsTrackerViewState.Loading,
                ),
                    onClickNext = {},
                    onClickPrev = {},
                    onClickDate = {},
                    writeNoteClick = {},
                    onNavIconClick = {},
                    onIconAddClick = {},
                    onClickSaveMood = {})
            }
        }
    }
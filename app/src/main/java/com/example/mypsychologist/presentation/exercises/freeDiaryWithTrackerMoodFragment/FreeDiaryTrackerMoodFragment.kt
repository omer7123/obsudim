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
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.mypsychologist.domain.entity.diaryEntity.CalendarEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodPresentEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.exercises.trackerMoodFragment.TrackerMoodViewModel
import com.example.mypsychologist.ui.core.CalendarView
    import com.example.mypsychologist.ui.core.IndicatorBottomSheet
    import com.example.mypsychologist.ui.core.PrimaryTextButton
import com.example.mypsychologist.ui.core.RecordItem
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
            val calendarViewState = viewModel.calendarViewState.collectAsState()
            val freeDiaryViewState = viewModel.freeDiaryViewState.collectAsState()
            val newMoodViewState = viewModel.newMoodViewState.collectAsState()
            val moodsViewState = viewModel.moodsViewState.collectAsState()

            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(color = AppTheme.colors.primaryBackground)

            FreeDiaryTrackerMoodScreenContent(
                calendarViewState = calendarViewState,
                freeDiaryViewState = freeDiaryViewState,
                newMoodViewState = newMoodViewState,
                moodsViewState = moodsViewState,
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


        @Composable
        fun FreeDiaryTrackerMoodScreenContent(
            calendarViewState: State<CalendarContent>,
            freeDiaryViewState: State<FreeDiaryViewState>,
            newMoodViewState: State<NewMoodStatusViewState>,
            moodsViewState: State<MoodsTrackerViewState>,
            onClickNext: () -> Unit,
            onClickPrev: () -> Unit,
            onClickDate: (Date) -> Unit,
            writeNoteClick: () -> Unit,
            onNavIconClick: () -> Unit,
            onIconAddClick: (Boolean) -> Unit,
            onClickSaveMood: () -> Unit,
        ) {
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp

            BottomSheetScaffold(sheetShape = RoundedCornerShape(
                topStart = 28.dp, topEnd = 28.dp,
            ),
                modifier = Modifier.safeDrawingPadding(),
                sheetContent = {
                    SheetContent(
                        freeDiaryViewState,
                        newMoodViewState,
                        moodsViewState,
                        writeNoteClick,
                        onIconAddClick,
                        onClickSaveMood
                    )
                },
                sheetPeekHeight = screenHeight - 400.dp,
                sheetBackgroundColor = AppTheme.colors.screenBackground,
                content = {
                    MainContent(
                        res = calendarViewState,
                        onClickNext = onClickNext,
                        onClickPrev = onClickPrev,
                        onClickDate = onClickDate,
                        onNavIconClick = onNavIconClick,
                    )
                })
        }

        @Composable
        fun MainContent(
            res: State<CalendarContent>,
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
                            tint = AppTheme.colors.screenBackground,
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
                    month = res.value.month,
                    years = res.value.year,
                    date = res.value.dates,
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
            freeDiaryViewState: State<FreeDiaryViewState>,
            newMoodViewState: State<NewMoodStatusViewState>,
            moodsViewState: State<MoodsTrackerViewState>,
            writeNoteClick: () -> Unit,
            onIconAddClick: (Boolean) -> Unit,
            onClickSaveMood: () -> Unit
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)

            ) {
                IndicatorBottomSheet(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = stringResource(id = R.string.notes),
                    style = AppTheme.typography.titleXS,
                    color = AppTheme.colors.primaryText,
                )

                FreeDiaryContent(
                    freeDiaryState = freeDiaryViewState,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                PrimaryTextButton(
                    textString = stringResource(id = R.string.write_note),
                    onClick = { writeNoteClick() },
                    modifier = Modifier.padding(top = 20.dp)
                )

                AddNewMoodContent(
                    newMoodViewState = newMoodViewState,
                    onClickSaveMood = { onClickSaveMood() },
                    onIconAddClick = { onIconAddClick(it) },
                    modifier = Modifier.padding(top = 40.dp),
                )
                val contentPaddingTopForMoods =
                    if (newMoodViewState.value is NewMoodStatusViewState.Content) 40
                    else 20

                MoodsContent(
                    moodsViewState,
                    modifier = Modifier.padding(top = contentPaddingTopForMoods.dp)
                )
            }
        }

        @Composable
        fun MoodsContent(moodsViewState: State<MoodsTrackerViewState>, modifier: Modifier) {
            when (val state = moodsViewState.value) {
                is MoodsTrackerViewState.Content -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = modifier
                    ) {
                        items(state.moods, key = { it.id }) { item ->
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
            newMoodViewState: State<NewMoodStatusViewState>,
            onClickSaveMood: () -> Unit,
            onIconAddClick: (Boolean) -> Unit,
            modifier: Modifier = Modifier
        ) {
            when (val state = newMoodViewState.value) {
                is NewMoodStatusViewState.Content -> {
                    Column(modifier = Modifier) {
                        Text(
                            text = stringResource(id = R.string.mood),
                            style = AppTheme.typography.titleXS,
                            color = AppTheme.colors.primaryText,
                            modifier = modifier
                        )

                        Slider(
                            value = state.mood,
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
                            text = stringResource(id = state.moodTitleIdSource),
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
                            modifier = Modifier.padding(top = 20.dp),
                            isLoading = state.loading
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
            freeDiaryState: State<FreeDiaryViewState>,
            modifier: Modifier = Modifier
        ) {
            when (val state = freeDiaryState.value) {
                is FreeDiaryViewState.Content -> {
                    if (state.freeDiaries.isNotEmpty()) {
                        LazyColumn(
                            contentPadding = PaddingValues(top = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier.heightIn(max = 300.dp)
                        ) {
                            items(state.freeDiaries, key = { it.id }) { record ->
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

        @Preview(showBackground = true)
        @Composable
        fun FreeDiaryTrackerMoodScreenContent_Preview() {
            val calendar = Calendar.getInstance()
            val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val dates = (1..daysInMonth).map {
                calendar.set(Calendar.DAY_OF_MONTH, it)
                CalendarEntity(calendar.time, it % 5 == 0, it % 3 == 0)
            }

            AppTheme {
                FreeDiaryTrackerMoodScreenContent(
                    calendarViewState = remember {
                        mutableStateOf(
                            CalendarContent(
                                month = Date(),
                                year = "2024",
                                dates = dates
                            )
                        )
                    },
                    freeDiaryViewState = remember {
                        mutableStateOf(
                            FreeDiaryViewState.Content(
                                freeDiaries = listOf(RecordExerciseEntity("ds", "Заметка 2", "9:30"))
                            )
                        )
                    },
                    newMoodViewState = remember {
                        mutableStateOf(
                            NewMoodStatusViewState.Content(
                                moodTitleIdSource = R.string.normal_mood
                            )
                        )
                    },
                    moodsViewState = remember { mutableStateOf(MoodsTrackerViewState.Loading) },
                    onClickNext = {},
                    onClickPrev = {},
                    onClickDate = {},
                    writeNoteClick = {},
                    onNavIconClick = {},
                    onIconAddClick = {},
                    onClickSaveMood = {}
                )
            }
        }
    }
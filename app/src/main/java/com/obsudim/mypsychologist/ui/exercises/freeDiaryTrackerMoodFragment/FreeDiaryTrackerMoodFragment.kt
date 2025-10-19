    package com.obsudim.mypsychologist.ui.exercises.freeDiaryTrackerMoodFragment

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
    import androidx.compose.foundation.lazy.grid.GridCells
    import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
    import androidx.compose.foundation.lazy.grid.items
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
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.core.os.bundleOf
    import androidx.fragment.app.Fragment
    import androidx.lifecycle.ViewModelProvider
    import androidx.navigation.NavController
    import androidx.navigation.fragment.findNavController
    import com.google.accompanist.systemuicontroller.rememberSystemUiController
    import com.obsudim.mypsychologist.R
    import com.obsudim.mypsychologist.domain.entity.diaryEntity.CalendarEntity
    import com.obsudim.mypsychologist.domain.entity.diaryEntity.MoodPresentEntity
    import com.obsudim.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity
    import com.obsudim.mypsychologist.extensions.getAppComponent
    import com.obsudim.mypsychologist.presentation.di.MultiViewModelFactory
    import com.obsudim.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.CalendarContent
    import com.obsudim.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.FreeDiaryViewState
    import com.obsudim.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.MoodsTrackerViewState
    import com.obsudim.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.NewMoodStatusViewState
    import com.obsudim.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.TrackerMoodViewModel
    import com.obsudim.mypsychologist.ui.core.composeComponents.CalendarView
    import com.obsudim.mypsychologist.ui.core.composeComponents.IndicatorBottomSheet
    import com.obsudim.mypsychologist.ui.core.composeComponents.PrimaryTextButton
    import com.obsudim.mypsychologist.ui.core.composeComponents.RecordItem
    import com.obsudim.mypsychologist.ui.core.composeComponents.SmileItem
    import com.obsudim.mypsychologist.ui.core.composeComponents.formatToDayString
    import com.obsudim.mypsychologist.ui.core.composeComponents.formatToMonthStringInf
    import com.obsudim.mypsychologist.ui.exercises.newFreeDiaryFragment.NewFreeDiaryFragment
    import com.obsudim.mypsychologist.ui.theme.AppTheme
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
                onClickSaveMood = { viewModel.saveMoodTrack() },
                onSmileClick = viewModel::onSmileClick,
                )
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
            onSmileClick: (Int) -> Unit,
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
                        onClickSaveMood,
                        onSmileClick = onSmileClick
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
            onClickSaveMood: () -> Unit,
            onSmileClick: (Int) -> Unit,
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
                    onSmileClick = onSmileClick
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
            onSmileClick: (Int) -> Unit,
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

                        Spacer(modifier = Modifier.padding(top = 30.dp))
                        
                        Text(
                            text = stringResource(R.string.emotions),
                            style = AppTheme.typography.titleXS,
                            color = AppTheme.colors.primaryText
                        )

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(5),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(vertical = 20.dp)
                        ) {
                            items(state.smiles) { smile->
                                val color =
                                    if (smile.id in state.selectedSmiles) AppTheme.colors.primaryBackground
                                    else AppTheme.colors.tertiaryBackground

                                SmileItem(
                                    bgColor = color,
                                    emoji = smile,
                                    onSmileClick = { onSmileClick(smile.id) }
                                )
                            }
                        }

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
            Column {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            AppTheme.colors.primaryBackground,
                            shape = RoundedCornerShape(28.dp)
                        )
                ) {

                    Text(
                        text = stringResource(id = item.moodTitleResStr),
                        style = AppTheme.typography.titleCygreSemiBold,
                        color = AppTheme.colors.primaryTextInvert,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 20.dp, top = 15.dp, bottom = 20.dp)
                    )
                    Text(
                        text = item.date,
                        style = AppTheme.typography.titleCygreSemiBold,
                        color = AppTheme.colors.tertiaryText,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 20.dp)

                    )
                }
                EmojiGrid(emojis = item.emojiTexts)

            }
        }

        @Composable
        fun EmojiGrid(emojis: List<String>) {
            var twoInRow = true
            var index = 0

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)) {
                while (index < emojis.size) {
                    val count = if (twoInRow) 2 else 1
                    val rowItems = emojis.subList(index, minOf(index + count, emojis.size))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = if (twoInRow) Arrangement.spacedBy(10.dp) else Arrangement.Center
                    ) {
                        for (emoji in rowItems) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = AppTheme.colors.tertiaryBackground,
                                        shape = RoundedCornerShape(28.dp)
                                    )
                                    .then(
                                        if (twoInRow) Modifier.weight(1f) else Modifier.fillMaxWidth()
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = emoji,
                                    style = AppTheme.typography.titleCygreFont,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                                )
                            }
                        }
                    }

                    twoInRow = !twoInRow
                    index += count
                }
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
                                freeDiaries = listOf(
                                    RecordExerciseEntity(
                                        "ds",
                                        "Заметка 2",
                                        "9:30"
                                    )
                                )
                            )
                        )
                    },
                    newMoodViewState = remember {
                        mutableStateOf(
                            NewMoodStatusViewState.Hide
//                                (
//                                moodTitleIdSource = R.string.normal_mood,
//                                smiles = listOf(
//                                    "\uD83D\uDE00", // 😀
//                                    "\uD83D\uDE01", // 😁
//                                    "\uD83D\uDE02", // 😂
//                                    "\uD83D\uDE03", // 🤣
//                                    "\uD83D\uDE04", // 😄
//                                    "\uD83D\uDE05", // 😅
//                                    "\uD83D\uDE06", // 😆
//                                    "\uD83D\uDE07", // 😇
//                                    "\uD83D\uDE08", // 😈
//                                    "\uD83D\uDE09", // 😉
//                                ),
//                                selectedSmiles = setOf("\uD83D\uDE09")
//                            )
                        )
                    },
                    moodsViewState = remember { mutableStateOf(MoodsTrackerViewState.Content(listOf(
                        MoodPresentEntity("f", 12, R.string.super_mood,
                            "19:28", listOf(
                                "\uD83D\uDE05", // 😅
                                    "\uD83D\uDE06", // 😆
                                    "\uD83D\uDE07", // 😇
                                    "\uD83D\uDE08", // 😈
                                    "\uD83D\uDE09", // 😉
                            )
                        ),

                    )

                    )) },
                    onClickNext = {},
                    onClickPrev = {},
                    onClickDate = {},
                    writeNoteClick = {},
                    onNavIconClick = {},
                    onIconAddClick = {},
                    onClickSaveMood = {},
                    onSmileClick = {}
                )
            }
        }
    }
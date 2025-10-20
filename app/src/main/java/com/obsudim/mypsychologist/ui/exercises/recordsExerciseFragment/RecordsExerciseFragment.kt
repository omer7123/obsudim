package com.obsudim.mypsychologist.ui.exercises.recordsExerciseFragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.exercises.recordsExerciseFragment.RecordsExerciseScreenState
import com.obsudim.mypsychologist.presentation.exercises.recordsExerciseFragment.RecordsExerciseViewModel
import com.obsudim.mypsychologist.ui.core.composeComponents.IndicatorBottomSheet
import com.obsudim.mypsychologist.ui.core.composeComponents.PrimaryTextButton
import com.obsudim.mypsychologist.ui.core.composeComponents.RecordItem
import com.obsudim.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class RecordsExerciseFragment : Fragment() {
    private var destinationNav: Int = R.id.action_fragment_diaries_to_fragment_new_diary

    @Inject
    lateinit var vmFactory: RecordsExerciseViewModel.Factory
    private val viewModel: RecordsExerciseViewModel by viewModels {
        vmFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        val exercise = ExerciseEntity(
            id = requireArguments().getString(EXERCISE_ID)!!,
            title = requireArguments().getString(EXERCISE_TITLE)!!,
            description = requireArguments().getString(EXERCISE_DESCRIPTION)!!,
            linkToPicture = requireArguments().getString(IMAGE)!!,
            closed = false
        )

        when(exercise.id){
            "DPG_ID" -> {
                viewModel.loadDiariesDPG()
                destinationNav = R.id.action_fragment_diaries_to_definitionProblemGroupExerciseFragment
            }
            "KPT_ID" -> {
//                viewModel.loadDiariesKPT()
            }
        }

        setContent {
            AppTheme{
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    DiariesScreen(viewModel = viewModel, exercise, findNavController())
                }
            }
        }
    }

    @Composable
    fun DiariesScreen(
        viewModel: RecordsExerciseViewModel,
        exercise: ExerciseEntity,
        navController: NavController
    ){
        val listViewState = viewModel.screenState.collectAsState()
        ExerciseContent(
            stateHistory = listViewState,
            exercise = exercise,
            addNewDiaryClick = {
                navController.navigate(
                    destinationNav,
                    bundleOf(
                        EXERCISE_ID to requireArguments().getString(EXERCISE_ID)
                    )
                )
            },
            navBackOnClick = {
                navController.popBackStack()
            }
        )
    }

    @Composable
    fun ExerciseContent(
        stateHistory: State<RecordsExerciseScreenState>,
        exercise: ExerciseEntity,
        addNewDiaryClick: () -> Unit,
        navBackOnClick: () -> Unit
    ) {
        val sheetMaxHeight = remember { mutableStateOf(0.dp) }
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp

        BottomSheetScaffold(
            sheetContent = {
                SheetContent(
                    recordsList = stateHistory,
                    exerciseData = exercise,
                    addNewDiaryClick = addNewDiaryClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = screenHeight - sheetMaxHeight.value)
                )
            },
            sheetPeekHeight = 500.dp,
            sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            sheetBackgroundColor = AppTheme.colors.screenBackground,
            content = {
                Box {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.ic_kpt_diary_bg),
                        contentDescription = ""
                    )

                    IconButton(
                        modifier = Modifier
                            .statusBarsPadding()
                            .align(Alignment.TopStart)
                            .padding(top = 20.dp, start = 16.dp)
                            .onGloballyPositioned { coordinates ->
                                sheetMaxHeight.value = coordinates.size.height.dp
                            },
                        onClick = {
                            navBackOnClick()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back_white),
                            contentDescription = "Назад",
                            tint = AppTheme.colors.screenBackground
                        )
                    }
                }
            },
        )
    }

    @Composable
    fun SheetContent(
        recordsList: State<RecordsExerciseScreenState>,
        exerciseData: ExerciseEntity,
        addNewDiaryClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier.padding(horizontal = 16.dp)) {
            IndicatorBottomSheet(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = exerciseData.title,
                style = AppTheme.typography.titleXS,
                color = AppTheme.colors.primaryText
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = exerciseData.description,
                style = AppTheme.typography.bodyL,
                color = AppTheme.colors.primaryText
            )
            PrimaryTextButton(
                modifier = Modifier.padding(top = 20.dp),
                textString = stringResource(id = R.string.begin),
                onClick = { addNewDiaryClick() }
            )
            Text(
                modifier = Modifier.padding(top = 40.dp),
                text = stringResource(id = R.string.history),
                style = AppTheme.typography.bodyXLBold,
                color = AppTheme.colors.primaryText
            )
            when(val res = recordsList.value){
                is RecordsExerciseScreenState.Content -> {
                    if (res.data.isNotEmpty()) {
                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(res.data) {
                                RecordItem(item = it)
                            }
                        }
                    }else{
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = stringResource(R.string.kpt_empty_placeholder),
                            style = AppTheme.typography.bodyL,
                            color = AppTheme.colors.primaryText
                        )
                    }
                }
                RecordsExerciseScreenState.Error -> {
                    Text(
                        text = stringResource(id = R.string.unknown_error_placeholder),
                        style = AppTheme.typography.bodyL,
                        color = AppTheme.colors.primaryText,
                        modifier = modifier.padding(top = 10.dp)
                    )
                }
                RecordsExerciseScreenState.Initial -> Unit
                RecordsExerciseScreenState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp))
                }
            }
        }
    }



    @Preview
    @Composable
    fun ExerciseContent_Preview() {
        AppTheme {
            ExerciseContent(
                stateHistory = remember{
                    mutableStateOf(
                        RecordsExerciseScreenState.Content(listOf(
                        RecordExerciseEntity(
                            id = "ifdfd",
                            title = "Первое проххождение",
                            date = "12-01-20"
                        ),
                        RecordExerciseEntity(
                            id = "ifdfd",
                            title = "Первое проххождение",
                            date = "12-01-20"
                        ),
                    )))
                },
                addNewDiaryClick = {},
                exercise = ExerciseEntity(
                    "fdf", "КПТ-дневник",
                    stringResource(R.string.kpt_desc),
                    "ds",
                    true
                ),
                navBackOnClick = {}
            )
        }
    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.loadDiaries(requireArguments().getString(EXERCISE_ID).toString())
//    }

    companion object {
        const val IMAGE = "IMAGE"
        const val EXERCISE_TITLE = "EXERCISE_TITLE"
        const val EXERCISE_DESCRIPTION = "EXERCISE_DESCRIPTION"
        const val EXERCISE_ID = "ID_EXERCISE"
    }
}


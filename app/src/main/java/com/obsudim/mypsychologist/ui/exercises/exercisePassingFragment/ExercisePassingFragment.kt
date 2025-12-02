package com.obsudim.mypsychologist.ui.exercises.exercisePassingFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.FieldAddableListChange
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.PagesExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.SaveExerciseResultResponseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.SectionsExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSection
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSectionUiRes
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.di.MultiViewModelFactory
import com.obsudim.mypsychologist.presentation.exercises.exercisePassingFragment.ExercisePassingScreenState
import com.obsudim.mypsychologist.presentation.exercises.exercisePassingFragment.ExercisePassingViewModel
import com.obsudim.mypsychologist.ui.core.composeComponents.PlaceholderError
import com.obsudim.mypsychologist.ui.core.composeComponents.TotalTextButton
import com.obsudim.mypsychologist.ui.core.composeComponents.exercisesComponents.AddableList
import com.obsudim.mypsychologist.ui.core.composeComponents.exercisesComponents.TextInputItem
import com.obsudim.mypsychologist.ui.core.composeComponents.exercisesComponents.TextInputItemDefault
import com.obsudim.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject


class ExercisePassingFragment : Fragment() {

    companion object{
        const val EXERCISE_ID = "EXERCISE_ID"
    }

    @Inject
    lateinit var vmFactory: MultiViewModelFactory
    private val viewModel: ExercisePassingViewModel by lazy {
        ViewModelProvider(this, vmFactory)[ExercisePassingViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)

        viewModel.getExerciseStructure(requireArguments().getString(EXERCISE_ID)!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {

        setContent {
            AppTheme {
                Scaffold(
                    modifier = Modifier
                        .imePadding()
                        .background(color = AppTheme.colors.screenBackground),
                    topBar = {
                        val viewState = viewModel.screenState.collectAsState().value
                        if (viewState !is ExercisePassingScreenState.SuccessSave) {
                            TopAppBar(
                                backgroundColor = AppTheme.colors.primaryBackground,
                                elevation = 0.dp
                            ) {
                                IconButton(
                                    onClick = { viewModel.btnClickPrev() }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_arrow_back_white),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    ExercisePassingScreen(viewModel, innerPadding)
                }
            }
        }
    }

    @Composable
    private fun ExercisePassingScreen(
        viewModel: ExercisePassingViewModel,
        innerPadding: PaddingValues
    ) {
        val viewState = viewModel.screenState.collectAsState().value

        when (viewState) {
            is ExercisePassingScreenState.Content -> {
                ExercisePassingContent(
                    viewState = viewState,
                    onTextInputChange = {viewModel.textInputChange(it)},
                    onNextBtnClick = { viewModel.btnClickNext() },
                    modifier = Modifier.padding(innerPadding),
                    onTextChangeAddableList = { viewModel.changeTextAddableList(it) },
                    addItemAddableListOnClick = { viewModel.addItemAddableList(it) }
                )
            }

            ExercisePassingScreenState.Error -> {
                PlaceholderError()
            }

            ExercisePassingScreenState.Initial -> Unit
            ExercisePassingScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is ExercisePassingScreenState.SuccessSave -> {
                FinishScreen(viewState.data)
            }
        }
    }

    @Composable
    private fun FinishScreen(data: SaveExerciseResultResponseEntity) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.primaryBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "+ ${data.score} баллов",
                    style = AppTheme.typography.titleCygreFont,
                    fontSize = 16.sp,
                    color = AppTheme.colors.primaryTextInvert,
                    modifier = Modifier
                        .background(
                            color = AppTheme.colors.navBackground,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 14.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { findNavController().popBackStack() },
                    modifier = Modifier
                        .background(
                            color = AppTheme.colors.screenBackground,
                            shape = RoundedCornerShape(28.dp)
                        )
                        .size(40.dp)

                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        tint = AppTheme.colors.primaryText,
                        contentDescription = null
                    )
                }
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("/dsa").build(),
                contentDescription = null,
                placeholder = ColorPainter(color = AppTheme.colors.loading),
                error = painterResource(id = R.drawable.ic_book_succ_passing_exercise),
                colorFilter = ColorFilter.tint(AppTheme.colors.secondaryBackground),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .height(350.dp)
            )

            Text(
                text = data.successMessage,
                style = AppTheme.typography.titleCygreFont,
                fontSize = 24.sp,
                color = AppTheme.colors.secondaryBackground,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp)
            )

            TotalTextButton(
                "В достижения",
                onClick = {},
                bgColor = AppTheme.colors.navBackground,
                modifier = Modifier.padding(top = 40.dp)
            )
        }
    }

    @Composable
    @Preview(showBackground = true)
    private fun FinishScreen_Preview() {
        AppTheme {
            FinishScreen(
                data =
                    SaveExerciseResultResponseEntity(
                        id = "ds",
                        20,
                        "ds",
                        "primary",
                        successMessage = "Вы разобрали свои\n" +
                                "«горячие точки»"
                    )
            )
        }
    }

    @Composable
    private fun ExercisePassingContent(
        viewState: ExercisePassingScreenState.Content,
        onTextInputChange: (TypeOfSectionUiRes.TextInputUiEntity) -> Unit,
        onNextBtnClick: () -> Unit,
        onTextChangeAddableList: (FieldAddableListChange) -> Unit,
        addItemAddableListOnClick: (String) -> Unit,
        modifier: Modifier = Modifier
        ) {
        val fieldsOfThisPage =
            viewState.pagesWithFields.first { it.pageNumber == viewState.currentPage }.sections

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .imePadding()
                .background(color = AppTheme.colors.screenBackground)

        ) {

            items(fieldsOfThisPage) { item ->
                val currValField =
                    viewState.currValue.first { it.id == item.id }

                ItemExercise(
                    item,
                    currValField,
                    onTextInputChange = { onTextInputChange(it) },
                    onTextChangeAddableList = { onTextChangeAddableList(it) },
                    addItemAddableList = { addItemAddableListOnClick(it) },
                )
            }

            item {
                if (viewState.currentPage == viewState.pagesWithFields.size - 1) {
                    TotalTextButton(
                        textString = stringResource(R.string.end),
                        onClick = {onNextBtnClick()},
                    )
                }else {
                    TotalTextButton(
                        textString = stringResource(R.string.next),
                        onClick = { onNextBtnClick() },
                    )
                }
            }
        }
    }

    @Composable
    private fun ItemExercise(
        item: SectionsExerciseEntity,
        currValField: TypeOfSectionUiRes,
        onTextInputChange: (TypeOfSectionUiRes.TextInputUiEntity) -> Unit,
        onTextChangeAddableList: (FieldAddableListChange) -> Unit,
        addItemAddableList: (String) -> Unit,
    ) {
        when(item.type){
            TypeOfSection.AddableList -> {
                AddableList(
                    item,
                    currValField as TypeOfSectionUiRes.AddableListUiEntity,
                    onTextChange = { onTextChangeAddableList(it) },
                    onAddItemClick = { addItemAddableList(it) }
                )
            }
            TypeOfSection.TextInput -> {
                TextInputExercise(
                    item = item,
                    currValField = currValField as TypeOfSectionUiRes.TextInputUiEntity,
                    onTextInputChange = {onTextInputChange(it)}
                )
            }
        }
    }

    @Composable
    private fun TextInputExercise(
        item: SectionsExerciseEntity,
        currValField: TypeOfSectionUiRes.TextInputUiEntity,
        onTextInputChange: (TypeOfSectionUiRes.TextInputUiEntity) -> Unit,
    ) {
        val view = item.view
        when (view) {
            "primary" -> {
                TextInputItem(
                    title = item.title,
                    text = currValField.title,
                    onTextChange = {
                        onTextInputChange(
                            TypeOfSectionUiRes.TextInputUiEntity(
                                item.id,
                                it
                            )
                        )
                    },
                    placeholder = item.placeholder,

                    )
            }
            "default" -> {
                TextInputItemDefault(
                    title = item.title,
                    text = currValField.title,
                    onTextChange = {
                        onTextInputChange(
                            TypeOfSectionUiRes.TextInputUiEntity(
                                item.id,
                                it
                            )
                        )
                    },
                    placeholder = item.placeholder,

                    )
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    private fun ExercisePassingContentPreview() {
        AppTheme {
            ExercisePassingContent(
                viewState = ExercisePassingScreenState.Content(
                    currentPage = 0,
                    pagesWithFields = listOf(
                        PagesExerciseEntity(
                            0,
                            sections = listOf(
                                SectionsExerciseEntity(
                                    id = "9a24ce66-8dd0-4008-bcf4-d06664cdd9aa",
                                    title = "Опишите ситуацию в максимальных деталях",
                                    view = "primary",
                                    type = TypeOfSection.TextInput,
                                    placeholder = "Что произошло?",
                                    prompt = "string",
                                    variants = emptyList()
                                )
                            )
                        )
                    ),
                    currValue = listOf(
                        TypeOfSectionUiRes.TextInputUiEntity(
                            idLoc = "9a24ce66-8dd0-4008-bcf4-d06664cdd9aa",
                        )
                    )
                ),
                {},
                {},
                {},
                {}
            )
        }
    }
}
data class Marsh(val stantionName: String, val depTime: String, val arrTime: String, val timeMin: Int)
data class ScheduleRes(val idLine: String, val stantionLineStart: String, val stantionLineEnd: String, val marshs: List<Marsh>, val totalTimeMin: Int)
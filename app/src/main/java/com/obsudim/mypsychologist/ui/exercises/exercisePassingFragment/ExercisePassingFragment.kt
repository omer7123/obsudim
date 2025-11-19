package com.obsudim.mypsychologist.ui.exercises.exercisePassingFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.PagesExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.SectionsExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSection
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSectionUiRes
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.di.MultiViewModelFactory
import com.obsudim.mypsychologist.presentation.exercises.exercisePassingFragment.ExercisePassingScreenState
import com.obsudim.mypsychologist.presentation.exercises.exercisePassingFragment.ExercisePassingViewModel
import com.obsudim.mypsychologist.ui.core.composeComponents.PlaceholderError
import com.obsudim.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject


class ExercisePassingFragment : Fragment() {

    @Inject
    lateinit var vmFactory: MultiViewModelFactory
    private val viewModel: ExercisePassingViewModel by lazy {
        ViewModelProvider(this, vmFactory)[ExercisePassingViewModel::class.java]
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                ExercisePassingScreen(viewModel)
            }
        }
    }

    @Composable
    private fun ExercisePassingScreen(viewModel: ExercisePassingViewModel) {
        val viewState = viewModel.screenState.collectAsState().value
        when (viewState) {
            is ExercisePassingScreenState.Content -> {
                ExercisePassingContent(viewState)
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
        }
    }

    @Composable
    private fun ExercisePassingContent(viewState: ExercisePassingScreenState.Content) {
        val fieldsOfThisPage =
            viewState.pagesWithFields.first { it.pageNumber == viewState.currentPage }.sections

        LazyColumn {
            items(fieldsOfThisPage) { item ->
                ItemExercise(item)
            }
        }
    }

    @Composable
    private fun ItemExercise(item: SectionsExerciseEntity) {
        when(item.type){
            TypeOfSection.AddableList -> {}
            TypeOfSection.TextInput -> {
                TextInputExercise(item)
            }
        }
    }

    @Composable
    private fun TextInputExercise(item: SectionsExerciseEntity) {
        val view = item.view
        when{
            view == "primary" ->{

            }
            view == "default" ->{
                
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
                            id = "9a24ce66-8dd0-4008-bcf4-d06664cdd9aa",
                        )
                    )
                )
            )
        }
    }
}
package com.obsudim.mypsychologist.ui.exercises.exerciseDemoResultFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseResultEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSection
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.di.MultiViewModelFactory
import com.obsudim.mypsychologist.presentation.exercises.exerciseDemoResultFragment.ExerciseDemoResultScreenState
import com.obsudim.mypsychologist.presentation.exercises.exerciseDemoResultFragment.ExerciseDemoResultViewModel
import com.obsudim.mypsychologist.ui.core.composeComponents.PlaceholderError
import com.obsudim.mypsychologist.ui.core.composeComponents.exercisesComponents.TextItemDefault
import com.obsudim.mypsychologist.ui.core.composeComponents.exercisesComponents.TextItemPrimary
import com.obsudim.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject


class ExerciseDemoResultFragment : Fragment() {
    companion object{
        private const val RESULT_ID = "RESULT_ID"
        private const val EXERCISE_ID = "EXERCISE_ID"
    }

    @Inject
    lateinit var vmFactory: MultiViewModelFactory
    private val viewModel: ExerciseDemoResultViewModel by lazy {
        ViewModelProvider(this, vmFactory)[ExerciseDemoResultViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onStart() {
        super.onStart()
        val idExercise = requireArguments().getString(EXERCISE_ID)!!
        val idResult = requireArguments().getString(RESULT_ID)!!
        viewModel.getExerciseResult(idExercise, idResult)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            backgroundColor = AppTheme.colors.tertiaryBackground,
                            elevation = 0.dp
                        ) {
                            IconButton(
                                onClick = { findNavController().popBackStack() }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_arrow_back_white),
                                    contentDescription = null
                                )
                            }
                        }

                    }
                ) { innerPadding ->

                    ExerciseDemoResultScreen(
                        innerPadding,
                        viewModel
                    )
                }
            }
        }
    }

    @Composable
    private fun ExerciseDemoResultScreen(
        innerPadding: PaddingValues,
        viewModel: ExerciseDemoResultViewModel
    ) {
        val viewState = viewModel.screenState.collectAsState().value
        when (viewState) {
            is ExerciseDemoResultScreenState.Content -> {
                ExerciseDemoResultContent(
                    viewState.fields,
                   innerPadding
                )
            }

            ExerciseDemoResultScreenState.Error -> {
                PlaceholderError()
            }

            ExerciseDemoResultScreenState.Initial -> Unit
            ExerciseDemoResultScreenState.Loading -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

    @Composable
    private fun ExerciseDemoResultContent(
        fields: List<ExerciseResultEntity>,
        innerPadding: PaddingValues,
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding())) {
            items(fields){item->
                CardItemExercise(item)
            }
        }
    }

    @Composable
    private fun CardItemExercise(item: ExerciseResultEntity) {
        when(item.type){
            TypeOfSection.AddableList -> TODO()
            TypeOfSection.TextInput -> CardInputType(item)
        }
    }

    @Composable
    private fun CardInputType(item: ExerciseResultEntity) {
        when(item.view){
            "primary" -> {
                TextItemPrimary(
                    item.title,
                    item.value
                )
            }
            "default" -> {
                TextItemDefault(
                    item.title,
                    item.value
                )
            }
        }
    }
}


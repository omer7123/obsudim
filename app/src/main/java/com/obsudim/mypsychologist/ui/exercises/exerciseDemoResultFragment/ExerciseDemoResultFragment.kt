package com.obsudim.mypsychologist.ui.exercises.exerciseDemoResultFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.di.MultiViewModelFactory
import com.obsudim.mypsychologist.presentation.exercises.exerciseDemoResultFragment.ExerciseDemoResultViewModel
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
                Scaffold {innerPadding ->
                    ExerciseDemoResultScreen(
                        innerPadding
                    )
                }
            }
        }
    }

    @Composable
    private fun ExerciseDemoResultScreen(innerPadding: PaddingValues) {

    }
}


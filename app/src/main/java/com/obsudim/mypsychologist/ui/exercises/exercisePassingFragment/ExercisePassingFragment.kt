package com.obsudim.mypsychologist.ui.exercises.exercisePassingFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.di.MultiViewModelFactory
import com.obsudim.mypsychologist.presentation.exercises.exercisePassingFragment.ExercisePassingViewModel
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

    }
}
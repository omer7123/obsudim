package com.example.mypsychologist.ui.exercises.rebt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentHarmfulThoughtsBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.presentation.exercises.ProblemAnalysisViewModel
import com.example.mypsychologist.presentation.exercises.ThoughtAnalysisScreenState
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.cbt.FragmentHint
import com.example.mypsychologist.ui.exercises.cbt.InputDelegate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RebtHarmfulThoughtFragment : Fragment() {

    private var binding: FragmentHarmfulThoughtsBinding by autoCleared()

    @Inject
    lateinit var vmFactory: ProblemAnalysisViewModel.Factory
    private val viewModel: ProblemAnalysisViewModel by activityViewModels { vmFactory }

    private lateinit var mainAdapter: MainAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHarmfulThoughtsBinding.inflate(inflater, container, false)

        binding.toolbar.toolbar.apply {
            title = getString(R.string.problem_analysis)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        setupAdapter()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        binding.refuteButton.setOnClickListener {
            if (viewModel.requirementIsCorrect())
                findNavController().navigate(R.id.fragment_rebt_alternative_thought)
        }

        return binding.root
    }

    private fun setupAdapter() {
        mainAdapter = MainAdapter().apply {
            addDelegate(
                InputDelegate(::showHint)
            )

            submitList(viewModel.harmfulThought)
        }

        binding.thoughtRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
            setHasFixedSize(true)
        }
    }

    private fun showHint(titleId: Int, hintId: Int) {
        FragmentHint.newInstance(titleId, hintId)
            .show(childFragmentManager, "tag")
    }

    private fun render(state: ThoughtAnalysisScreenState) {
        when (state) {
            is ThoughtAnalysisScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    requireContext().showToast(getString(R.string.network_error))
            }

            is ThoughtAnalysisScreenState.Data -> {
                binding.progressBar.isVisible = false
                mainAdapter.submitList(state.saved.first)
            }

            is ThoughtAnalysisScreenState.ValidationError -> {
                mainAdapter.submitList(state.listWithErrors)
            }

            is ThoughtAnalysisScreenState.Init -> Unit
            is ThoughtAnalysisScreenState.RequestResult -> Unit
        }
    }
}
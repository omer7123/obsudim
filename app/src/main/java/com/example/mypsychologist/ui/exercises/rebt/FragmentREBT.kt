package com.example.mypsychologist.ui.exercises.rebt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentRebtBinding
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.presentation.exercises.REBTScreenState
import com.example.mypsychologist.presentation.exercises.REBTViewModel
import com.example.mypsychologist.setupCard
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentREBT : Fragment() {
    private lateinit var binding: FragmentRebtBinding

    @Inject
    lateinit var vmFactory: REBTViewModel.Factory
    private val viewModel: REBTViewModel by viewModels{ vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRebtBinding.inflate(inflater, container, false)

        setupCards()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        setupListeners()

        return binding.root
    }

    private fun setupCards() {
        binding.apply {
            setupCard(
                problemAnalysis,
                R.string.problem_analysis,
                R.string.problem_analysis_signature
            )
            setupCard(beliefsCheck, R.string.beliefs_check, R.string.beliefs_check_signature)
            setupCard(
                beliefsAnalysis,
                R.string.beliefs_analysis,
                R.string.beliefs_analysis_signature
            )
            setupCard(dialog, R.string.dialog, R.string.dialog_signature)
        }
    }

    private fun render(it: REBTScreenState) {
        when (it) {
            is REBTScreenState.Data -> {
                setupData(it)
            }
            is REBTScreenState.Init -> {}
            is REBTScreenState.Loading -> {}
            is REBTScreenState.Error -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupData(it: REBTScreenState.Data) {
        it.problemProgress.apply {
            binding.problem.text = problem

            if (problemAnalysisCompleted)
                binding.problemAnalysis.card.background =
                    getDrawable(requireContext(), R.drawable.primary_card)
            if (beliefsCheckCompleted)
                binding.beliefsCheck.card.background =
                    getDrawable(requireContext(), R.drawable.primary_card)
            if (beliefsAnalysisCompleted)
                binding.beliefsAnalysis.card.background =
                    getDrawable(requireContext(), R.drawable.primary_card)
            if (dialogCompleted)
                binding.dialog.card.background =
                    getDrawable(requireContext(), R.drawable.primary_card)
        }
    }

    private fun setupListeners() {
        binding.changeButton.setOnClickListener {
            childFragmentManager.setFragmentResultListener(
                ProblemsFragment.PROBLEM,
                viewLifecycleOwner
            ) { _, bundle ->
                viewModel.getProblemProgress(bundle.getInt(ProblemsFragment.PROBLEM))
            }

            ProblemsFragment().show(childFragmentManager, TAG)
        }
    }

    companion object {
        const val TAG = "change_problem"
    }
}
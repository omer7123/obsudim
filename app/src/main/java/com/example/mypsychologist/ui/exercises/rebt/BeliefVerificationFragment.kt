package com.example.mypsychologist.ui.exercises.rebt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentBeliefVerificationBinding
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.exercises.BeliefVerificationViewModel
import com.example.mypsychologist.presentation.exercises.NewThoughtDiaryScreenState
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.cbt.FragmentHint
import com.example.mypsychologist.ui.exercises.cbt.SeekBarDelegate
import com.example.mypsychologist.ui.exercises.cbt.ThoughtDiaryDelegate
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class BeliefVerificationFragment : Fragment() {

    private var binding: FragmentBeliefVerificationBinding by autoCleared()

    @Inject
    lateinit var vmFactory: BeliefVerificationViewModel.Factory
    private val viewModel: BeliefVerificationViewModel by viewModels { vmFactory }

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
        binding = FragmentBeliefVerificationBinding.inflate(inflater, container, false)

        binding.saveButton.setOnClickListener {
            viewModel.tryToSaveBeliefVerification(requireArguments().getString(TYPE)!!)
        }

        setupFields()
        setupAdapter(viewModel.items)

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun setupFields() {
        binding.beliefType.text = renderType(requireArguments().getString(TYPE)!!)
        binding.belief.text = requireArguments().getString(BELIEF)
    }

    private fun renderType(it: String) =
        getString(
            when (it) {
                ProblemAnalysisEntity::dogmaticRequirement.name -> R.string.dogmatic_requirement
                ProblemAnalysisEntity::dramatization.name -> R.string.dramatization
                ProblemAnalysisEntity::lft.name -> R.string.LFT
                ProblemAnalysisEntity::humiliatingRemarks.name -> R.string.humiliating_remarks
                ProblemAnalysisEntity::flexiblePreference.name -> R.string.flexible_preference
                ProblemAnalysisEntity::perspective.name -> R.string.perspective
                ProblemAnalysisEntity::hft.name -> R.string.HFT
                else -> R.string.unconditional_acceptance
            }
        )


    private fun setupAdapter(items: List<DelegateItem>) {
        mainAdapter = MainAdapter().apply {
            addDelegate(
                ThoughtDiaryDelegate(::showHint)
            )
            submitList(items)
        }

        binding.questionsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())

            adapter = mainAdapter
        }
    }

    private fun showHint(titleId: Int, hintId: Int) {
        FragmentHint.newInstance(titleId, hintId)
            .show(childFragmentManager, "tag")
    }

    private fun render(state: NewThoughtDiaryScreenState) {
        when (state) {
            is NewThoughtDiaryScreenState.RequestResult -> {
                renderRequest(state.success)
            }

            is NewThoughtDiaryScreenState.ValidationError -> {
                mainAdapter.submitList(state.listWithErrors)
            }

            is NewThoughtDiaryScreenState.Init -> Unit
        }
    }

    private fun renderRequest(isSuccess: Boolean) {

        when {
            !isSuccess -> {
                showToast(getString(R.string.db_error))
            }

            !isNetworkConnect() -> {
                Snackbar.make(
                    binding.coordinator,
                    R.string.save_after_connect,
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.go) {
                    setResult()
                }.show()
            }

            else -> {
                setResult()
                showToast(getString(R.string.success))
            }
        }
    }

    private fun setResult() {
        setFragmentResult(FINISH, bundleOf())
    }

    companion object {
        const val FINISH = "finish"
        private const val TYPE = "type"
        private const val BELIEF = "belief"

        fun newInstance(type: String, belief: String) =
            BeliefVerificationFragment().apply {
                arguments = bundleOf(TYPE to type, BELIEF to belief)
            }
    }
}
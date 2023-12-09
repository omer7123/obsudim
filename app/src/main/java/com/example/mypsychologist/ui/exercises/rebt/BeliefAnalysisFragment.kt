package com.example.mypsychologist.ui.exercises.rebt

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentBeliefVerificationBinding
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.exercises.BeliefAnalysisViewModel
import com.example.mypsychologist.presentation.exercises.BeliefVerificationScreenState
import com.example.mypsychologist.presentation.exercises.NewThoughtDiaryScreenState
import com.example.mypsychologist.renderRebtBeliefType
import com.example.mypsychologist.showHint
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.cbt.ThoughtDiaryDelegate
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class BeliefAnalysisFragment : Fragment() {

    private var binding: FragmentBeliefVerificationBinding by autoCleared()

    @Inject
    lateinit var vmFactory: BeliefAnalysisViewModel.Factory
    private val viewModel: BeliefAnalysisViewModel by viewModels {
        BeliefAnalysisViewModel.provideFactory(
            vmFactory, requireArguments().getString(
                TYPE, ""
            )
        )
    }

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


        setupFields()
        setupAdapter(viewModel.items)

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        binding.saveButton.setOnClickListener {
            viewModel.tryToSaveBeliefAnalysis(
                requireArguments().getString(
                    TYPE
                )!!
            )
        }

        return binding.root
    }

    private fun setupFields() {
        binding.beliefType.text = renderRebtBeliefType(requireArguments().getString(TYPE)!!)
        binding.belief.text = requireArguments().getString(BELIEF)
    }

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

    private fun render(state: BeliefVerificationScreenState) {
        when (state) {
            is BeliefVerificationScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    showToast(getString(R.string.network_error))
            }
            is BeliefVerificationScreenState.Data -> {
                binding.progressBar.isVisible = false
                mainAdapter.submitList(state.saved)
            }
            is BeliefVerificationScreenState.RequestResult -> {
                binding.progressBar.isVisible = false
                renderRequest(state.success)
            }
            is BeliefVerificationScreenState.ValidationError -> {
                mainAdapter.submitList(state.listWithErrors)
            }
            is BeliefVerificationScreenState.Init -> Unit
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
            BeliefAnalysisFragment().apply {
                arguments = bundleOf(TYPE to type, BELIEF to belief)
            }
    }
}
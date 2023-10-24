package com.example.mypsychologist.ui.exercises.rebt

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.ChangeProblemBottomSheetBinding
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.presentation.exercises.ProblemsScreenState
import com.example.mypsychologist.presentation.exercises.ProblemsViewModel
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProblemsFragment : BottomSheetDialogFragment() {

    private var binding: ChangeProblemBottomSheetBinding by autoCleared()
    private lateinit var mainAdapter: MainAdapter

    @Inject
    lateinit var vmFactory: ProblemsViewModel.Factory
    private val viewModel: ProblemsViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChangeProblemBottomSheetBinding.inflate(inflater, container, false)

        setupAdapter()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)


        binding.addButton.setOnClickListener {
            childFragmentManager.setFragmentResultListener(
                NewProblemFragment.NEW_PROBLEM, viewLifecycleOwner
            ) { _, bundle ->
                viewModel.add(bundle.getString(NewProblemFragment.TITLE)!!, bundle.getString(NewProblemFragment.ID)!!)
            }

            NewProblemFragment().show(childFragmentManager, TAG)
        }

        return binding.root
    }

    private fun setupAdapter() {
        binding.problemsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            mainAdapter = MainAdapter().apply {
                addDelegate(ProblemsDelegate {problemId ->
                    viewModel.markAsCurrent(problemId)
                    setFragmentResult(PROBLEM, bundleOf(PROBLEM to problemId))
                    dismiss()
                })
            }
            adapter = mainAdapter
        }
    }

    private fun render(it: ProblemsScreenState) {
        when (it) {
            is ProblemsScreenState.Data -> {
                mainAdapter.submitList(it.problems.toDelegateItems())
            }
            is ProblemsScreenState.Init -> {}
            is ProblemsScreenState.Loading -> {}
            is ProblemsScreenState.Error -> {
                showToast(getString(R.string.network_error))
            }
        }
    }

    companion object {
        private const val TAG = "new problem"
        const val PROBLEM = "problem"
    }
}
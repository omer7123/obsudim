package com.example.mypsychologist.ui.exercises.rebt

import android.content.Context
import android.os.Bundle
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
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.presentation.ProblemsScreenState
import com.example.mypsychologist.presentation.ProblemsViewModel
import com.example.mypsychologist.ui.MainAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProblemsFragment : BottomSheetDialogFragment() {

    private lateinit var binding: ChangeProblemBottomSheetBinding
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

        }

        return binding.root
    }

    private fun setupAdapter() {
        binding.problemsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            mainAdapter = MainAdapter().apply {
                addDelegate(ProblemsDelegate{
                    setFragmentResult(PROBLEM, bundleOf(PROBLEM to it))
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
                Toast.makeText(
                    requireContext(),
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        const val PROBLEM = "problem"
    }
}
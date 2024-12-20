package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestsBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.diagnostics.TestsScreenState
import com.example.mypsychologist.presentation.diagnostics.TestsViewModel
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentTests : Fragment() {

    private var binding: FragmentTestsBinding by autoCleared()
    private lateinit var mainAdapter: MainAdapter

    @Inject
    lateinit var vmFactory: TestsViewModel.Factory
    private val viewModel: TestsViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().diagnosticComponent().create().inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestsBinding.inflate(inflater, container, false)

        binding.include.apply {
            toolbar.title = getString(R.string.diagnostics)
            profileIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_profile)
            }
            psychologistsIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_psychologists_with_tasks)
            }
        }


//        setupAdapter(state.data)


        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                renderState(state)
//                mainAdapter.submitList(it)
            }
            .launchIn(lifecycleScope)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTests()
    }

    private fun renderState(state: TestsScreenState) {
        when (state) {
            is TestsScreenState.Content -> setupAdapter(state.data)
            is TestsScreenState.Error -> {
                binding.progressCircular.isVisible = false
                binding.testsRw.isVisible = false
                requireContext().showToast(state.msg)
            }
            TestsScreenState.Initial -> {}
            TestsScreenState.Loading -> {
                binding.testsRw.isVisible = false
                binding.progressCircular.isVisible = true
            }
        }
    }

    private fun setupAdapter(data: List<DelegateItem>) {

//        val onTestGroupClick: (TestGroupEntity, Boolean) -> Unit = { category, isChecked ->
//            if (isChecked) {
//                viewModel.setupTestsFor(category)
//            } else {
//                viewModel.hintTestsFor(category.titleId)
//            }
//        }

        binding.progressCircular.isVisible = false
        binding.testsRw.isVisible = true

        val onTestClick: (String, String, String) -> Unit = { testId, description, testTitle ->
            DiagnosticDialogFragment.newInstance(testId, testTitle, description)
                .show(childFragmentManager, DiagnosticDialogFragment.TAG)

        }
        binding.testsRw.apply {

            layoutManager = GridLayoutManager(requireContext(), 2)

            adapter = MainAdapter().apply {
//            addDelegate(TestGroupDelegate(onTestGroupClick))
                addDelegate(TestDelegate(onTestClick))
                submitList(data)
            }
        }

    }
}
package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.databinding.FragmentTestsBinding
import com.example.mypsychologist.domain.entity.TestGroupEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.diagnostics.TestHistoryViewModel
import com.example.mypsychologist.presentation.diagnostics.TestsViewModel
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

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        setupAdapter()


        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach {
                mainAdapter.submitList(it)
            }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun setupAdapter() {

        val onTestGroupClick: (TestGroupEntity, Boolean) -> Unit = { category, isChecked ->
            if (isChecked) {
                viewModel.setupTestsFor(category)
            } else {
                viewModel.hintTestsFor(category.titleId)
            }
        }

        val onTestClick: (Int, Int) -> Unit = { titleId, description ->
            DiagnosticDialogFragment.newInstance(titleId, description, TestHistoryViewModel.OWN)
                .show(childFragmentManager, DiagnosticDialogFragment.TAG)

        }

        mainAdapter = MainAdapter().apply {
            addDelegate(TestGroupDelegate(onTestGroupClick))
            addDelegate(TestDelegate(onTestClick))
        }

        binding.testsRw.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
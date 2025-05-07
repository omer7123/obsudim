package com.example.mypsychologist.ui.diagnostics.testResultFragment

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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.databinding.FragmentTestResultBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.diagnostics.testResultFragment.TestResultViewModel
import com.example.mypsychologist.ui.core.adapter.MainAdapter
import com.example.mypsychologist.ui.core.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TestResultFragment : Fragment() {

    private var binding: FragmentTestResultBinding by autoCleared()

    private lateinit var mainAdapter: MainAdapter

    @Inject
    lateinit var vmFactory: TestResultViewModel.Factory
    private val viewModel: TestResultViewModel by viewModels {
        TestResultViewModel.provideFactory(
            vmFactory,
            requireArguments().getString(TestResultViewModel.TEST_RESULT_ID, "")
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().diagnosticComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestResultBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.result)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
        setupAdapter()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun render(resource: Resource<TestResultsGetEntity>) {
        when(resource) {
            is Resource.Loading -> {
                binding.progressBar.isVisible = true
            }
            is Resource.Success -> {
                binding.progressBar.isVisible = false
//                binding.title.text = requireArguments().getString(TEST_TITLE, "")

                mainAdapter.submitList(resource.data.toDelegateItems() + resource.data.toConclusionDelegateItems())

            }
            is Resource.Error -> {
                requireContext().showToast(resource.msg.toString())
            }
        }
    }

    private fun setupAdapter() {
        mainAdapter = MainAdapter().apply {
            addDelegate(
                TestScaleResultDelegate()
            )
            addDelegate(
                DescScaleResultDelegate()
            )
        }

        binding.scalesRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }
    }

    companion object {
        const val TEST_TITLE = "test_title"
    }
}
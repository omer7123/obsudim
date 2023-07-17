package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestHistoryBinding
import com.example.mypsychologist.domain.entity.TestResultEntity
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.presentation.TestHistoryScreenState
import com.example.mypsychologist.presentation.TestHistoryViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentTestHistory : Fragment() {

    private lateinit var binding: FragmentTestHistoryBinding

    @Inject
    lateinit var vmFactory: TestHistoryViewModel.Factory
    private val viewModel: TestHistoryViewModel by viewModels {
        TestHistoryViewModel.provideFactory(
            vmFactory,
            requireArguments().getInt(TEST_TITLE_ID)
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
        binding = FragmentTestHistoryBinding.inflate(inflater, container, false)

        binding.title.text = getString(requireArguments().getInt(TEST_TITLE_ID))

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun render(state: TestHistoryScreenState) {
        when (state) {
            is TestHistoryScreenState.Loading -> {
                binding.progressBar.isVisible = true
            }
            is TestHistoryScreenState.Data -> {
                binding.progressBar.isVisible = false
                setupAdapter(state.results)
            }
            is TestHistoryScreenState.Error -> {
                binding.progressBar.isVisible = false
                Toast.makeText(
                    requireContext(),
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
            }
            is TestHistoryScreenState.Init -> {}
        }
    }

    private fun setupAdapter(list: List<TestResultEntity>) {

        binding.resultsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = TestHistoryAdapter(list)
        }
    }

    companion object {
        const val TEST_TITLE_ID = "test title id"
    }
}
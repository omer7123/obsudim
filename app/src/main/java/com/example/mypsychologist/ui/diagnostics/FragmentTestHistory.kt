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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestHistoryBinding
import com.example.mypsychologist.domain.entity.TestResultEntity
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.diagnostics.TestHistoryScreenState
import com.example.mypsychologist.presentation.diagnostics.TestHistoryViewModel
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentTestHistory : Fragment() {

    private var binding: FragmentTestHistoryBinding by autoCleared()

    @Inject
    lateinit var vmFactory: TestHistoryViewModel.Factory
    private val viewModel: TestHistoryViewModel by viewModels {
        TestHistoryViewModel.provideFactory(
            vmFactory,
            requireArguments().getInt(TEST_TITLE_ID),
            requireArguments().getString(CLIENT_ID)!!
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
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun render(state: TestHistoryScreenState) {
        when (state) {
            is TestHistoryScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else {
                    binding.includePlaceholder.layout.isVisible = true
                }
            }
            is TestHistoryScreenState.Data -> {
                binding.progressBar.isVisible = false
                binding.includePlaceholder.layout.isVisible = false
                if(state.results.isNotEmpty())
                    setupAdapter(state.results)
                else
                    showPlaceholderForEmptyList()
            }
            is TestHistoryScreenState.Error -> {
                binding.progressBar.isVisible = false
                showToast(getString(R.string.db_error))
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

    private fun showPlaceholderForEmptyList() {
        binding.includePlaceholder.apply {
            image.setImageResource(R.drawable.ic_import_contacts)
            title.text = getString(R.string.nothing)
            text.text = getString(R.string.no_tests)
            layout.isVisible = true
        }
    }

    companion object {
        const val TEST_TITLE_ID = "test title id"
        const val CLIENT_ID = "client id"
    }
}
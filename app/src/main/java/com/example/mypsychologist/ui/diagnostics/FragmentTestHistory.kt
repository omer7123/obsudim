package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.presentation.diagnostics.TestHistoryScreenState
import com.example.mypsychologist.presentation.diagnostics.TestHistoryViewModel
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.ui.autoCleared
import com.github.mikephil.charting.data.RadarEntry
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentTestHistory : Fragment() {

    private var binding: FragmentTestHistoryBinding by autoCleared()

    @Inject
    lateinit var vmFactory: TestHistoryViewModel.Factory
    private val viewModel: TestHistoryViewModel by viewModels { vmFactory }

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

        binding.title.text = requireArguments().getString(TEST_TITLE)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("ID", requireArguments().getString(TEST_ID).toString())
        viewModel.loadHistory(requireArguments().getString(TEST_ID).toString())
    }

    private fun render(state: TestHistoryScreenState) {
        when (state) {
            is TestHistoryScreenState.Loading -> {
                if (isNetworkConnect()) {
                    binding.radar.isVisible = false
                    binding.progressBar.isVisible = true
                }else {
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
                requireContext().showToast(getString(R.string.db_error))
            }
            is TestHistoryScreenState.Init -> {}
        }
    }

    private fun setupAdapter(list: List<TestResultsGetEntity>) {
        binding.radar.isVisible = true
//        val listScale: List<Te>
        for (elem in list){

        }
        val entries1 = listOf(
            RadarEntry(54f), // Тревога
            RadarEntry(60f), // Выгорание
            RadarEntry(65f), // РПП
            RadarEntry(65f), // РПП
        )
        val labels = listOf("Сила", "Ловкость", "Скорость", "Ум")
        binding.radar.updateData(entries1, labels, 78f)

//        binding.resultsRw.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
//            adapter = TestHistoryAdapter(list)
//        }
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
        const val TEST_ID = "test_id"
        const val TEST_TITLE = "test_title"
    }
}
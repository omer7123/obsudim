package com.example.mypsychologist.ui.psychologist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestsBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.diagnostics.TestsViewModel
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.diagnostics.FragmentTestHistory
import com.example.mypsychologist.ui.diagnostics.TestDelegate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentClientTests : Fragment() {
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
//                mainAdapter.submitList(it)
                              }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun setupAdapter() {

//        val onTestGroupClick: (TestGroupEntity, Boolean) -> Unit = { category, isChecked ->
//            if (isChecked) {
//                viewModel.setupTestsFor(category)
//            } else {
//                viewModel.hintTestsFor(category.titleId)
//            }
//        }

        val onTestClick: (String, String) -> Unit = { titleId, _ ->
//            findNavController().navigate(
//                R.id.fragment_test_history, bundleOf(
//                    FragmentTestHistory.TEST_TITLE_ID to titleId,
//                    FragmentTestHistory.CLIENT_ID to requireArguments().getString(CLIENT_ID)
//                )
//            )
        }

        mainAdapter = MainAdapter().apply {
//            addDelegate(TestGroupDelegate(onTestGroupClick))
//            addDelegate(TestDelegate(onTestClick))
        }

        binding.testsRw.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
        const val CLIENT_ID = "client id"
    }
}
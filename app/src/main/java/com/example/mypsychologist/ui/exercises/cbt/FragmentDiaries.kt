package com.example.mypsychologist.ui.exercises.cbt

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentDiariesBinding
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.ThoughtDiariesScreenState
import com.example.mypsychologist.presentation.ThoughtDiariesViewModel
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentDiaries : Fragment() {

    private var binding: FragmentDiariesBinding by autoCleared()
    private lateinit var adapter: MainAdapter

    @Inject
    lateinit var vmFactory: ThoughtDiariesViewModel.Factory
    private val viewModel: ThoughtDiariesViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiariesBinding.inflate(inflater, container, false)

        setupToolbarAndFAB()

        setupAdapter()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun setupToolbarAndFAB() {
        binding.include.toolbar.apply {
            title = getString(R.string.thought_diary)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }

        binding.newDiaryFab.setOnClickListener {
            findNavController().navigate(R.id.fragment_new_diary)
        }
    }

    private fun setupAdapter() {
        adapter = MainAdapter().apply {
            addDelegate(
                RecordDelegate { id ->
                    findNavController().navigate(
                        R.id.fragment_diary,
                        bundleOf(FragmentThoughtDiary.ID to id)
                    )
                }
            )

            binding.recordsRw.adapter = this
            binding.recordsRw.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun render(it: ThoughtDiariesScreenState) {
        when (it) {
            is ThoughtDiariesScreenState.Data -> {
                binding.progressBar.isVisible = false
                binding.includePlaceholder.layout.isVisible = false

                if (it.records.isNotEmpty())
                    adapter.submitList(it.records.toDelegateItems())
                else {
                    showPlaceholderForEmptyList()
                }
            }
            is ThoughtDiariesScreenState.Init -> {}
            is ThoughtDiariesScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    binding.includePlaceholder.layout.isVisible = true
            }
            is ThoughtDiariesScreenState.Error -> {
                binding.progressBar.isVisible = false
                showToast(getString(R.string.network_error))
            }
        }
    }

    private fun showPlaceholderForEmptyList() {
        binding.includePlaceholder.apply {
            image.setImageResource(R.drawable.ic_import_contacts)
            title.text = getString(R.string.nothing)
            text.text = getString(R.string.no_diaries)
            layout.isVisible = true
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadDiaries()
    }
}
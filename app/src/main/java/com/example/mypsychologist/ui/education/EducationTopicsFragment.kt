package com.example.mypsychologist.ui.education

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentEducationTopicsBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.ListScreenState
import com.example.mypsychologist.presentation.education.EducationTopicsViewModel
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class EducationTopicsFragment : Fragment() {

    private var binding: FragmentEducationTopicsBinding by autoCleared()

    @Inject
    lateinit var vmFactory: EducationTopicsViewModel.Factory
    private val viewModel: EducationTopicsViewModel by viewModels { vmFactory }

    private lateinit var mainAdapter: MainAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().educationComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEducationTopicsBinding.inflate(inflater, container, false)

        binding.include.apply{
            toolbar.title = getString(R.string.psychoeducation)
            profileIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_profile)
            }
            psychologistsIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_psychologists_with_tasks)
            }
        }

        setupAdapter()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        viewModel.getTopics()
        return binding.root
    }

    private fun setupAdapter() {
        mainAdapter = MainAdapter().apply {
            addDelegate(TopicsDelegate { item ->
                findNavController().navigate(R.id.fragment_education, bundleOf(
                    EducationFragment.TOPIC_TAG to item.id,
                ))
            })
        }
        binding.topicsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
            setHasFixedSize(true)
        }
    }

    private fun render(state: ListScreenState) {
        when(state) {
            is ListScreenState.Loading -> {
                if(!isNetworkConnect())
                    requireContext().showToast(getString(R.string.network_error))
                else
                    binding.progressBar.isVisible = true
            }
            is ListScreenState.Data -> {
                binding.progressBar.isVisible = false
                mainAdapter.submitList(state.items)
            }
            is ListScreenState.Error -> {
                binding.progressBar.isVisible = false
                requireContext().showToast(getString(R.string.db_error))
            }
            is ListScreenState.Init -> Unit
        }
    }
}
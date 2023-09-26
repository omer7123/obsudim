package com.example.mypsychologist.ui.psychologist

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
import com.example.mypsychologist.databinding.FragmentOwnPsychologistsBinding
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.ListScreenState
import com.example.mypsychologist.presentation.psychologist.PsychologistsWithTasksViewModel
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PsychologistsWithTasksFragment : Fragment() {

    private var binding: FragmentOwnPsychologistsBinding by autoCleared()

    @Inject
    lateinit var vmFactory: PsychologistsWithTasksViewModel.Factory
    private val viewModel: PsychologistsWithTasksViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().psychologistComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOwnPsychologistsBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.my_psychologists)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.findPsychologistButton.setOnClickListener {
            findNavController().navigate(R.id.fragment_psychologists)
        }

        binding.toAllButton.setOnClickListener {
            findNavController().navigate(R.id.fragment_psychologists)
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun render(state: ListScreenState) {
        when (state) {
            is ListScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    showToast(getString(R.string.network_error))
            }
            is ListScreenState.Data -> {

                binding.progressBar.isVisible = false

                if (state.items.isEmpty())
                    binding.placeholder.isVisible = true
                else {
                    binding.placeholder.isVisible = false
                    setupAdapter(state.items)
                }
            }
            is ListScreenState.Error -> {
                showToast(getString(R.string.db_error))
            }
            is ListScreenState.Init -> Unit
        }
    }

    private fun setupAdapter(items: List<DelegateItem>) {
        binding.psychologistsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())

            adapter = MainAdapter().apply {
                addDelegate(OwnPsychologistDelegate { psychologistId ->

                })
                addDelegate(TaskDelegate { taskId, psychologistId, isChecked ->
                    viewModel.markTask(taskId, psychologistId, isChecked)
                })

                submitList(items)
            }
        }
    }
}
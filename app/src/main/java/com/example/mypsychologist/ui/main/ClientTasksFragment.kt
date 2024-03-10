package com.example.mypsychologist.ui.main

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
import com.example.mypsychologist.databinding.FragmentClientTasksBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.presentation.main.ClientTasksViewModel
import com.example.mypsychologist.presentation.psychologist.TasksScreenState
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.FragmentEditField
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ClientTasksFragment : Fragment() {

    private var binding: FragmentClientTasksBinding by autoCleared()

    @Inject
    lateinit var vmFactory: ClientTasksViewModel.Factory
    private val viewModel: ClientTasksViewModel by viewModels {
        ClientTasksViewModel.provideFactory(vmFactory, requireArguments().getString(ID)!!)
    }

    private lateinit var tasksAdapter: MainAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClientTasksBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.tasks)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }

        setupAdapter()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        setupFABListener()

        return binding.root
    }

    private fun setupAdapter() {
        binding.tasksRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            tasksAdapter = MainAdapter().apply {
                addDelegate(ClientTaskDelegate {
                    viewModel.deleteTask(it)
                })
            }
            adapter = tasksAdapter
        }
    }

    private fun render(state: TasksScreenState) {
        when (state) {
            is TasksScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    requireContext().showToast(getString(R.string.network_error))
            }
            is TasksScreenState.Data -> {
                binding.progressBar.isVisible = false
                tasksAdapter.submitList(state.items.toDelegateItem())
            }
            is TasksScreenState.Error -> {
                requireContext().showToast(getString(R.string.network_error))
            }
            is TasksScreenState.Init -> Unit
        }
    }

    private fun setupFABListener() {
        binding.addTaskFab.setOnClickListener {
            childFragmentManager.setFragmentResultListener(
                ADD_TASK, viewLifecycleOwner
            ) { _, bundle ->

                bundle.getString(FragmentEditField.NEW_TEXT)?.let { text ->
                    viewModel.addTask(text)
                }
            }

            FragmentEditField.newInstance(
                getString(R.string.add_task),
                ""
            ).show(childFragmentManager, ADD_TASK)
        }
    }

    companion object {
        const val ID = "id"
        private const val ADD_TASK = "add task"
    }
}
package com.example.mypsychologist.ui.main

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
import com.example.mypsychologist.*
import com.example.mypsychologist.databinding.FragmentClientInfoBinding
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.extensions.toYears
import com.example.mypsychologist.presentation.main.ClientInfoScreenState
import com.example.mypsychologist.presentation.main.ClientInfoViewModel
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.psychologist.ExercisesFragment
import com.example.mypsychologist.ui.psychologist.FragmentClientTests
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

class ClientInfoFragment : Fragment() {

    private var binding: FragmentClientInfoBinding by autoCleared()

    @Inject
    lateinit var vmFactory: ClientInfoViewModel.Factory

    private val viewModel: ClientInfoViewModel by viewModels {
        ClientInfoViewModel.provideFactory(
            vmFactory,
            requireArguments().getString(ID)!!
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentClientInfoBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.client)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        setupListeners()

        return binding.root
    }

    private fun render(state: ClientInfoScreenState) {
        when (state) {
            is ClientInfoScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    requireContext().showToast(getString(R.string.network_error))
            }
            is ClientInfoScreenState.Data -> {
                binding.progressBar.isVisible = false
                setupFields(state.info)
            }
            is ClientInfoScreenState.Error -> {
                binding.progressBar.isVisible = false
                requireContext().showToast(getString(R.string.db_error))
            }
            is ClientInfoScreenState.Init -> Unit
        }
    }

    private fun setupFields(info: ClientInfoEntity) {
        binding.apply {
            name.text = info.name
            age.text = info.birthday
            gender.text = getString(R.string.gender_pattern, info.gender)
        }
        setupChips(info.request.map { it.text })
    }

    private fun setupChips(list: List<String>) {
        binding.specializationsGroup.removeAllViews()

        list.forEach { specialization ->
            binding.specializationsGroup.addView(
                Chip(requireContext()).apply {
                    text = specialization
                    isClickable = false
                }
            )
        }
    }

    private fun setupListeners() {
        binding.testsLayout.setOnClickListener {
            findNavController().navigate(R.id.fragment_client_tests, bundleOf(
                FragmentClientTests.CLIENT_ID to requireArguments().getString(ID)
            ))
        }
        binding.exercisesLayout.setOnClickListener {
            findNavController().navigate(R.id.fragment_exercises_list, bundleOf(
                ExercisesFragment.CLIENT_ID to requireArguments().getString(ID)
            ))
        }
        binding.tasksLayout.setOnClickListener {
            findNavController().navigate(R.id.fragment_client_tasks, bundleOf(
                ClientTasksFragment.ID to requireArguments().getString(ID)
            ))
        }
    }

    companion object {
        const val ID = "id"
    }
}
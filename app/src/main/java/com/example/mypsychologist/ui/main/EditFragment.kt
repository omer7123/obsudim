package com.example.mypsychologist.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.*
import com.example.mypsychologist.databinding.FragmentEditBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.main.EditScreenState
import com.example.mypsychologist.presentation.main.EditViewModel
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class EditFragment : Fragment() {

    private var binding: FragmentEditBinding by autoCleared()

    @Inject
    lateinit var vmFactory: EditViewModel.Factory
    private val viewModel: EditViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.edit_data)
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

    private fun render(state: EditScreenState) {
        when (state) {
            is EditScreenState.CurrentData -> {

            }

            is EditScreenState.Loading -> {
                if (!isNetworkConnect())
                    requireContext().showToast(getString(R.string.network_error))
            }

            is EditScreenState.Response -> {
                if (state.result)
                    requireContext().showToast(getString(R.string.success))
                else
                    requireContext().showToast(getString(R.string.db_error))
            }

            is EditScreenState.Init -> Unit
        }
    }


    private fun setupListeners() {
        binding.apply {

            changeRequestButton.setOnClickListener {

                childFragmentManager.setFragmentResultListener(
                    EDIT_REQUEST, viewLifecycleOwner
                ) { _, bundle ->

                    bundle.getStringArray(TagsFragment.TAGS)?.let { request ->
                        viewModel.changeRequest(request.toList())
                        setupChips(request.toList())
                    }
                }

                TagsFragment.newInstance().show(childFragmentManager, EDIT_REQUEST)
            }
        }
    }

    private fun setupChips(list: List<String>) {
        binding.requestsGroup.removeAllViews()
        list.forEach {
            binding.requestsGroup.addView(
                Chip(requireContext()).apply {
                    text = it
                }
            )
        }
    }

    companion object {
        private const val EDIT_NAME = "edit name"
        private const val EDIT_GENDER = "edit gender"
        private const val EDIT_DIAGNOSIS = "edit diagnosis"
        private const val EDIT_REQUEST = "edit request"
    }


}
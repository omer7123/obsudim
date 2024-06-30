package com.example.mypsychologist.ui.main

import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.*
import com.example.mypsychologist.databinding.FragmentEditBinding
import com.example.mypsychologist.domain.entity.TagEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.parcelable
import com.example.mypsychologist.extensions.parcelableArray
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.main.EditScreenState
import com.example.mypsychologist.presentation.main.EditViewModel
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.cbt.InputDelegate
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class EditFragment : Fragment() {

    private var binding: FragmentEditBinding by autoCleared()

    @Inject
    lateinit var vmFactory: EditViewModel.Factory
    private val viewModel: EditViewModel by viewModels { vmFactory }

    private lateinit var mainAdapter: MainAdapter

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
        setupAdapter(viewModel.items)

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
                requireContext().showToast(state.result.toString())
            }

            is EditScreenState.ValidationError -> {

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

                    bundle.parcelableArray<TagEntity>(TagsFragment.TAGS)?.let { request ->
                        viewModel.setRequest(request.toList())
                        setupChips(request.toList())
                    }
                }

                TagsFragment.newInstance().show(childFragmentManager, EDIT_REQUEST)
            }

            saveButton.setOnClickListener {
                viewModel.tryToSaveInfo()
            }
        }
    }

    private fun setupAdapter(items: List<DelegateItem>) {
        mainAdapter = MainAdapter().apply {
            addDelegate(
                InputDelegate()
            )

            submitList(items)
        }

        binding.itemsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }
    }

    private fun setupChips(list: List<TagEntity>) {
        binding.requestsGroup.removeAllViews()
        list.forEach {
            binding.requestsGroup.addView(
                Chip(requireContext()).apply {
                    text = it.text
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
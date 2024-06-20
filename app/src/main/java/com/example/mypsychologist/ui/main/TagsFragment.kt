package com.example.mypsychologist.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.ChipsBottomSheetBinding
import com.example.mypsychologist.domain.entity.TagEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.TagsScreenState
import com.example.mypsychologist.presentation.TagsViewModel
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TagsFragment : BottomSheetDialogFragment() {

    private var binding: ChipsBottomSheetBinding by autoCleared()
    private val checkedChips = mutableListOf<TagEntity>()

    @Inject
    lateinit var vmFactory: TagsViewModel.Factory
    private val viewModel: TagsViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().tagsComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChipsBottomSheetBinding.inflate(inflater, container, false)

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        setupListeners()

        return binding.root
    }

    private fun render(state: TagsScreenState) {
        when (state) {
            is TagsScreenState.Loading -> {}
            is TagsScreenState.Data -> {
                setupChips(state.items)
            }
            is TagsScreenState.Error -> { Log.d("Tags Error", "aaaaaaa")}
            is TagsScreenState.Init -> Unit
        }
    }

    private fun setupChips(tags: List<TagEntity>) {

        tags.forEach {
            val chip = layoutInflater.inflate(
                R.layout.filter_chip,
                binding.chipGroup,
                false
            ) as Chip

            binding.chipGroup.addView(
                chip.apply {
                    text = it.text
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked)
                            checkedChips.add(it)
                        else
                            checkedChips.remove(it)
                    }
                }
            )
        }
    }

    private fun setupListeners() {
        binding.apply {
            cancelButton.setOnClickListener {
                dismiss()
            }
            saveButton.setOnClickListener {
                setFragmentResult(
                    tag!!,
                    bundleOf(TAGS to checkedChips.toTypedArray())
                )
                dismiss()
            }
        }
    }

    companion object {
        const val TAGS = "tags"

        fun newInstance() =
            TagsFragment()
    }
}
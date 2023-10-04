package com.example.mypsychologist.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.ChipsBottomSheetBinding
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class ChipsFragment : BottomSheetDialogFragment() {

    private var binding: ChipsBottomSheetBinding by autoCleared()
    private val checkedChips = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChipsBottomSheetBinding.inflate(inflater, container, false)

        requireArguments().getStringArray(CHIPS)?.forEach {
            val chip = layoutInflater.inflate(
                R.layout.filter_chip,
                binding.chipGroup,
                false
            ) as Chip

            binding.chipGroup.addView(
                chip.apply {
                    text = it
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked)
                            checkedChips.add(it)
                        else
                            checkedChips.remove(it)
                    }
                }
            )
        }

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            cancelButton.setOnClickListener {
                dismiss()
            }
            saveButton.setOnClickListener {
                setFragmentResult(
                    tag!!,
                    bundleOf(CHIPS to checkedChips.toTypedArray())
                )
                dismiss()
            }
        }
    }

    companion object {
        const val CHIPS = "chips"

        fun newInstance(chips: Array<String>) =
            ChipsFragment().apply {
                arguments = bundleOf(CHIPS to chips)
            }
    }
}
package com.example.mypsychologist.ui.core

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.mypsychologist.databinding.FragmentConfirmationDialogBinding

class ConfirmationDialogFragment : DialogFragment() {

    private var binding: FragmentConfirmationDialogBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmationDialogBinding.inflate(inflater, container, false)

        binding.text.text = getString(requireArguments().getInt(TEXT))

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.yesButton.setOnClickListener {
            setFragmentResult(tag!!, bundleOf(RESULT to true))
            dismiss()
        }
        binding.noButton.setOnClickListener {
            setFragmentResult(tag!!, bundleOf(RESULT to false))
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

    companion object {
        private const val TEXT = "text"
        const val RESULT = "result"

        fun newInstance(textId: Int) =
            ConfirmationDialogFragment().apply {
                arguments = bundleOf(
                    TEXT to textId
                )
            }
    }
}
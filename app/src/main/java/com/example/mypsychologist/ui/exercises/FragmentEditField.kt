package com.example.mypsychologist.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.EditFieldBottomSheetBinding
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentEditField : BottomSheetDialogFragment() {

    private var binding: EditFieldBottomSheetBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditFieldBottomSheetBinding.inflate(inflater, container, false)

        binding.inputLayout.hint = getString(requireArguments().getInt(FIELD_ID))
        binding.field.setText(requireArguments().getString(CURRENT_TEXT))

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            cancelButton.setOnClickListener {
                dismiss()
            }
            saveButton.setOnClickListener {
                if (field.text.isNullOrEmpty()) {
                    field.error = getString(R.string.necessary_to_fill)
                } else {
                    setFragmentResult(tag!!, bundleOf(NEW_TEXT to field.text.toString()))
                    dismiss()
                }
            }
        }
    }

    companion object {
        private const val CURRENT_TEXT = "current_text"
        private const val FIELD_ID = "field_id"
        const val NEW_TEXT = "new_text"

        fun newInstance(fieldId: Int, currentText: String) =
            FragmentEditField().apply {
                arguments = bundleOf(
                    FIELD_ID to fieldId,
                    CURRENT_TEXT to currentText
                )
            }
    }
}
package com.example.mypsychologist.ui.exercises.cbt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.mypsychologist.databinding.HintBottomSheetBinding
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentHint : BottomSheetDialogFragment() {

     private var binding: HintBottomSheetBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = HintBottomSheetBinding.inflate(inflater, container, false)

        setupText()

        return binding.root
    }

    private fun setupText() {
        binding.apply {
            title.text = getString(requireArguments().getInt(TITLE_ID))
            text.text = getString(requireArguments().getInt(TEXT_ID))
        }
    }

    companion object {
        private const val TITLE_ID = "title"
        private const val TEXT_ID = "text"

        fun newInstance(titleId: Int, textId: Int) =
            FragmentHint().apply {
                arguments = bundleOf(
                    TITLE_ID to titleId,
                    TEXT_ID to textId
                )
            }
    }
}
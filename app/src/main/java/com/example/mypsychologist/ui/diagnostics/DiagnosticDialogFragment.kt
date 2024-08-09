package com.example.mypsychologist.ui.diagnostics

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentDiagnosticDialogBinding
import com.example.mypsychologist.ui.autoCleared

class DiagnosticDialogFragment : DialogFragment() {


    private var binding: FragmentDiagnosticDialogBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiagnosticDialogBinding.inflate(inflater, container, false)

        setupTitleAndText()

        setupListeners()

        return binding.root
    }

    private fun setupTitleAndText() {
        binding.apply {
            title.text = requireArguments().getString(TITLE_ID)
            text.text = requireArguments().getString(DESCRIPTION_ID)
        }
    }

    private fun setupListeners() {
        binding.historyButton.setOnClickListener {
            findNavController().navigate(
                R.id.fragment_test_history, bundleOf(
                    FragmentTestHistory.TEST_ID to requireArguments().getString(TEST_ID),
                    FragmentTestHistory.TEST_TITLE to requireArguments().getString(TITLE_ID)
                )
            )
        }

        binding.goButton.setOnClickListener {
            navigateToTest()
            dismiss()
        }
    }

    private fun navigateToTest() {
        val testId = requireArguments().getString(TEST_ID)

        findNavController().navigate(
            R.id.passingTestFragment, bundleOf(
                PassingTestFragment.TEST_ID to testId,
                PassingTestFragment.TITLE to requireArguments().getString(TITLE_ID),
                PassingTestFragment.DESCRIPTION to requireArguments().getString(DESCRIPTION_ID)
            )
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

    companion object {
        fun newInstance(testId: String, titleId: String, descriptionId: String) =
            DiagnosticDialogFragment().apply {
                arguments = bundleOf(
                    TITLE_ID to titleId,
                    DESCRIPTION_ID to descriptionId,
                    TEST_ID to testId
                )
            }

        const val TAG = "test_dialog"
        private const val TITLE_ID = "title"
        private const val DESCRIPTION_ID = "description"
        private const val TEST_ID = "test_id"
    }
}
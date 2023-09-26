package com.example.mypsychologist.ui.diagnostics

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentDiagnosticDialogBinding
import com.example.mypsychologist.presentation.diagnostics.DiagnosticDialogViewModel
import com.example.mypsychologist.ui.autoCleared

class DiagnosticDialogFragment : DialogFragment() {

    private val viewModel: DiagnosticDialogViewModel by viewModels()
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
            title.text = getString(requireArguments().getInt(TITLE_ID))
            text.text = getString(requireArguments().getInt(DESCRIPTION_ID))
        }
    }

    private fun setupListeners() {
        binding.historyButton.setOnClickListener {
            findNavController().navigate(
                R.id.fragment_test_history, bundleOf(
                    FragmentTestHistory.TEST_TITLE_ID to requireArguments().getInt(TITLE_ID),
                    FragmentTestHistory.CLIENT_ID to requireArguments().getString(CLIENT_ID)
                )
            )
        }

        binding.goButton.setOnClickListener {
            navigateToTest()
        }
    }

    private fun navigateToTest() {
        val titleId = requireArguments().getInt(TITLE_ID)

        viewModel.getScreenIdFor(titleId)?.let { screenId ->
            findNavController().navigate(screenId)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

    companion object {
        fun newInstance(titleId: Int, descriptionId: Int, clientId: String) =
            DiagnosticDialogFragment().apply {
                arguments = bundleOf(
                    TITLE_ID to titleId,
                    DESCRIPTION_ID to descriptionId,
                    CLIENT_ID to clientId
                )
            }

        const val TAG = "test_dialog"
        private const val TITLE_ID = "title"
        private const val CLIENT_ID = "client id"
        private const val DESCRIPTION_ID = "description"
    }
}
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
import com.example.mypsychologist.presentation.diagnostics.TestHistoryViewModel
import com.example.mypsychologist.ui.autoCleared

class TestResultDialogFragment : DialogFragment() {

    private var binding: FragmentDiagnosticDialogBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiagnosticDialogBinding.inflate(inflater, container, false)

        binding.apply {
            goButton.text = getString(R.string.main)

            title.text = requireArguments().getInt(SCORE).toString()
            text.text = requireArguments().getString(CONCLUSION)
        }

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.goButton.setOnClickListener {
            findNavController().navigate(R.id.main_fragment)
        }
        binding.historyButton.setOnClickListener {
            findNavController().navigate(
                R.id.fragment_test_history,
                bundleOf(
                    FragmentTestHistory.TEST_TITLE_ID to requireArguments().getInt(TITLE_ID),
                    FragmentTestHistory.CLIENT_ID to TestHistoryViewModel.OWN
                )
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

    companion object {
        fun newInstance(score: Int, conclusion: String, titleId: Int) =
            TestResultDialogFragment().apply {
                arguments = bundleOf(SCORE to score, CONCLUSION to conclusion, TITLE_ID to titleId)
            }

        const val TAG = "test_dialog"
        const val TITLE_ID = "title id"
        const val SCORE = "score"
        const val CONCLUSION = "conclusion"
    }
}
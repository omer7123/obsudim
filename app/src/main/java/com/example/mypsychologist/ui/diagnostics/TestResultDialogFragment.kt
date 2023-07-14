package com.example.mypsychologist.ui.diagnostics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentDiagnosticDialogBinding

class TestResultDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDiagnosticDialogBinding

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
        binding.goButton.setOnClickListener {

        }
    }
    companion object {
        fun newInstance(score: Int, conclusion: String) =
            TestResultDialogFragment().apply {
                arguments = bundleOf(SCORE to score, CONCLUSION to conclusion)
            }

        const val TAG = "test_dialog"
        const val SCORE = "score"
        const val CONCLUSION = "conclusion"
    }
}
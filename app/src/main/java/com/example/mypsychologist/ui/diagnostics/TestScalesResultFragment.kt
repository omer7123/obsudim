package com.example.mypsychologist.ui.diagnostics

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentDiagnosticScalesDialogBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.ConclusionOfTestEntity
import com.example.mypsychologist.extensions.parcelableArray
import com.example.mypsychologist.ui.autoCleared

class TestScalesResultFragment : DialogFragment() {

    private var binding: FragmentDiagnosticScalesDialogBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiagnosticScalesDialogBinding.inflate(inflater, container, false)

        val results: List<ConclusionOfTestEntity>? =
            requireArguments().parcelableArray<ConclusionOfTestEntity>(key = SCALES)?.toList()

        binding.apply {
            goButton.text = getString(R.string.main)
        }

        Log.e("RESULTS", results.toString())
        if (results != null) {
            setupAdapter(results)
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
                R.id.action_passingTestFragment_to_fragment_test_history,
                bundleOf(
                    FragmentTestHistory.TEST_ID to requireArguments().getString(TEST_ID),
                    FragmentTestHistory.TEST_TITLE to requireArguments().getString(TITLE)
                )
            )
        }
    }

    private fun setupAdapter(scales: List<ConclusionOfTestEntity>) {
        scales.let { items ->
            binding.scalesRw.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ScalesAdapter(scales)
                setHasFixedSize(true)
                isVisible = true
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

    companion object {
        fun newInstance(
            titleId: String,
            testId: String,
            scales: List<ConclusionOfTestEntity>
        ) =
            TestScalesResultFragment().apply {
                arguments = bundleOf(
                    TITLE to titleId,
                    TEST_ID to testId,
                    SCALES to scales.toTypedArray(),
                )
            }

        const val TAG = "test_scales_dialog"
        const val TITLE = "title"
        const val TEST_ID = "test_id"
        const val SCALES = "scales"
    }
}
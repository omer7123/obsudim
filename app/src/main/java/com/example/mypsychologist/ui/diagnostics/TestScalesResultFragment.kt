package com.example.mypsychologist.ui.diagnostics

import android.app.Dialog
import android.os.Bundle
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
import com.example.mypsychologist.presentation.diagnostics.TestHistoryViewModel
import com.example.mypsychologist.extensions.serializable
import com.example.mypsychologist.ui.autoCleared

class TestScalesResultFragment : DialogFragment() {

    private var binding: FragmentDiagnosticScalesDialogBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiagnosticScalesDialogBinding.inflate(inflater, container, false)

        binding.apply {
            goButton.text = getString(R.string.main)

            requireArguments().serializable<HashMap<Int, Pair<Int, Int>>>(SCALES)
                ?.let { setupAdapter(it) }
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

    private fun setupAdapter(scales: Map<Int, Pair<Int, Int>>) {
        scales.let { items ->
            binding.scalesRw.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ScalesAdapter(items.toList())
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
            titleId: Int,
            scales: HashMap<Int, Pair<Int, Int>>
        ) =
            TestScalesResultFragment().apply {
                arguments = bundleOf(
                    TITLE_ID to titleId,
                    SCALES to scales
                )
            }

        const val TAG = "test_scales_dialog"
        const val TITLE_ID = "title id"
        const val SCALES = "scales"
    }
}
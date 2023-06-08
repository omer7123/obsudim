package com.example.mypsychologist.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.databinding.ChangeProblemBottomSheetBinding
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProblemsFragment : BottomSheetDialogFragment() {

    private lateinit var binding: ChangeProblemBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChangeProblemBottomSheetBinding.inflate(inflater, container, false)

        setupAdapter()

        binding.addButton.setOnClickListener {

        }

        return binding.root
    }

    private fun setupAdapter() {
        val items = listOf(
            ProblemEntity("Проблема 1", true),
            ProblemEntity("Проблема 2", false, true),
            ProblemEntity("Проблема 3")
        )                                                               //
        binding.problemsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ProblemsAdapter(items) {
                setFragmentResult(PROBLEM, bundleOf(PROBLEM to it))
                dismiss()
            }
        }
    }

    companion object {
        const val PROBLEM = "problem"
    }
}
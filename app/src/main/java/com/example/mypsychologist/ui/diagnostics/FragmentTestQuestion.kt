package com.example.mypsychologist.ui.diagnostics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.TestBottomSheetBinding
import com.example.mypsychologist.domain.entity.TestQuestionEntity
import com.example.mypsychologist.parcelable
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentTestQuestion : BottomSheetDialogFragment() {

    private var binding: TestBottomSheetBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TestBottomSheetBinding.inflate(inflater, container, false)

        binding.progress.text = getString(
            R.string.test_progress,
            requireArguments().getInt(NUMBER).toString(),
            requireArguments().getInt(COUNT).toString()
        )

        setupListeners()

        setupAdapter()

        return binding.root
    }

    private fun setupListeners() {
        if (requireArguments().getInt(NUMBER) > 1) {

            binding.backButton.setOnClickListener {
                setFragmentResult(GO_BACK, bundleOf())
                dismiss()
            }
        }
    }

    private fun setupAdapter() {

        binding.answerVariantsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())

            adapter = AnswersAdapter(
                requireArguments().parcelable<TestQuestionEntity>(ANSWER_VARIANTS)!!.variants
            ) { score ->
                setFragmentResult(ANSWER, bundleOf(SCORE to score))
                dismiss()
            }

            setHasFixedSize(true)
        }
    }

    companion object {
        const val GO_BACK = "go back"
        const val ANSWER = "answer"
        const val SCORE = "score"
        private const val ANSWER_VARIANTS = "answer variants"
        private const val NUMBER = "number"
        private const val COUNT = "count"

        fun newInstance(question: TestQuestionEntity, number: Int, count: Int) =
            FragmentTestQuestion().apply {
                arguments = bundleOf(
                    ANSWER_VARIANTS to question,
                    NUMBER to number,
                    COUNT to count
                )
                isCancelable = false
            }
    }
}

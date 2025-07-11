package com.example.mypsychologist.ui.diagnostics.passingTestFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mypsychologist.databinding.FragmentTestQuestionBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.example.mypsychologist.extensions.parcelable
import com.example.mypsychologist.ui.core.autoCleared
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentTestQuestion : BottomSheetDialogFragment() {

    private var binding: FragmentTestQuestionBinding by autoCleared()
    private lateinit var question: QuestionOfTestEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        question = requireArguments().parcelable(ANSWER_VARIANTS)!!
        binding = FragmentTestQuestionBinding.inflate(inflater, container, false)

        if (question.number != 0) {
            binding.question.apply {
                text = question.text
                isVisible = true
            }
        }
        setupAdapter()
        return binding.root
    }

    private fun setupAdapter() {
        binding.answerVariantsRw.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = AnswersAdapter(question.answerOptions) { score ->
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

        fun newInstance(question: QuestionOfTestEntity, number: Int, count: Int) =
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

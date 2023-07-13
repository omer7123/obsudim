package com.example.mypsychologist.ui.diagnostics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.databinding.TestBottomSheetBinding
import com.example.mypsychologist.domain.entity.TestQuestionEntity
import com.example.mypsychologist.parcelable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentTestQuestion : BottomSheetDialogFragment() {
    private lateinit var binding: TestBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TestBottomSheetBinding.inflate(inflater, container, false)

        if (!requireArguments().getBoolean(IS_FIRST)) {

            binding.backButton.setOnClickListener {
                setFragmentResult(GO_BACK, bundleOf())
                dismiss()
            }
        }

        setupAdapter()

        return binding.root
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
        const val ANSWER_VARIANTS = "answer variants"
        const val IS_FIRST = "is first"
    }
}

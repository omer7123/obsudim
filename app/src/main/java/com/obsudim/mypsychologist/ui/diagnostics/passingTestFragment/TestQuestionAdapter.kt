package com.obsudim.mypsychologist.ui.diagnostics.passingTestFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.databinding.FragmentTestQuestionBinding
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity

class TestQuestionAdapter(
    private val questions: List<QuestionOfTestEntity>,
    private val totalNumber: Int,
    private val onAnswerClick: (Int) -> Unit,
    private val onBackClick: () -> Unit
) :
    RecyclerView.Adapter<TestQuestionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            FragmentTestQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), totalNumber, onAnswerClick, onBackClick
        )

    override fun getItemCount() = questions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(questions[position], position)
    }

    class ViewHolder(
        private val binding: FragmentTestQuestionBinding,
        private val totalNumber: Int,
        private val onAnswerClick: (Int) -> Unit,
        private val onBackClick: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(question: QuestionOfTestEntity, position: Int) {
            binding.score.text =
                itemView.context.getString(
                    R.string.test_progress,
                    (position + 1).toString(),
                    totalNumber.toString()
                )

            binding.question.text = question.text

            binding.toolbar.toolbar.setNavigationOnClickListener { onBackClick() }

            setupAdapter(question)
        }

        private fun setupAdapter(question: QuestionOfTestEntity) {

            binding.answerVariantsRw.apply {
                layoutManager = setupLayoutManager(question.answerOptions.size)

                adapter = AnswersAdapter(question.answerOptions, onAnswerClick)

                setHasFixedSize(true)
            }
        }

        private fun setupLayoutManager(optionsSize: Int) =
            if (optionsSize > MAX_OPTIONS_SIZE) {

                GridLayoutManager(itemView.context, SPAN_COUNT)
            }
            else
                LinearLayoutManager(itemView.context)

        companion object {
            private const val SPAN_COUNT = 2
            private const val MAX_OPTIONS_SIZE = 5
        }

    }

}
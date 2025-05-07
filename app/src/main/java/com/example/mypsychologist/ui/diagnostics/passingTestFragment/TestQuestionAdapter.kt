package com.example.mypsychologist.ui.diagnostics.passingTestFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestQuestionBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity

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
            binding.toolbar.toolbar.title =
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
                layoutManager = LinearLayoutManager(itemView.context)

                adapter = AnswersAdapter(question.answerOptions, onAnswerClick)

                setHasFixedSize(true)
            }
        }

    }

}
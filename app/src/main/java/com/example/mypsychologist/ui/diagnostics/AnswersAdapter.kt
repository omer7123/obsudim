package com.example.mypsychologist.ui.diagnostics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.AnswerVariantItemBinding
import com.example.mypsychologist.domain.entity.TestAnswerVariantEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.AnswersOfQuestionsEntity

class AnswersAdapter(
    private val items: List<AnswersOfQuestionsEntity>,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AnswerVariantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(
        private val binding: AnswerVariantItemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AnswersOfQuestionsEntity) {
            binding.text.text = item.text

            itemView.setOnClickListener { onClick(item.score) }
        }
    }
}
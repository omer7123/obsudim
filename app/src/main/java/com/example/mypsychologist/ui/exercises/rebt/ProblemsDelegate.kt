package com.example.mypsychologist.ui.exercises.rebt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.ProblemItemBinding
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class ProblemsDelegate(private val onClick: (String) -> Unit) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        ProblemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int) {
        (holder as ViewHolder).bind(item.content() as Pair<String, ProblemEntity>)
    }

    override fun isOfViewType(item: DelegateItem) = item is ProblemDelegateItem

    class ViewHolder(
        private val binding: ProblemItemBinding,
        private val onClick: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(problem: Pair<String, ProblemEntity>) {
            binding.problem.text = problem.second.title
            if (problem.second.actual)
                binding.background.setBackgroundResource(R.drawable.selected_problem_back)
            if (problem.second.completed) {
                binding.check.isVisible = true
                binding.problem.setTextColor(itemView.context.resources.getColor(R.color.md_theme_light_primary))
            }
            itemView.setOnClickListener {
                onClick(problem.first)
            }
        }
    }
}
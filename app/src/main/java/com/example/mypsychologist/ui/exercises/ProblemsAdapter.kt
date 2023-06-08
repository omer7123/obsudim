package com.example.mypsychologist.ui.exercises

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.ProblemItemBinding
import com.example.mypsychologist.domain.entity.ProblemEntity

class ProblemsAdapter(
    private val items: List<ProblemEntity>,
    private val onClick: (String) -> Unit
) :
    RecyclerView.Adapter<ProblemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProblemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context.resources)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount() = items.size


    class ViewHolder(private val binding: ProblemItemBinding, private val resources: Resources) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(problem: ProblemEntity, onClick: (String) -> Unit) {
            binding.problem.text = problem.title
            if (problem.actual)
                binding.background.setBackgroundResource(R.drawable.selected_problem_back)
            if (problem.completed) {
                binding.check.isVisible = true
                binding.problem.setTextColor(resources.getColor(R.color.md_theme_light_primary))
            }
            itemView.setOnClickListener {
                onClick(problem.title)
            }
        }
    }
}
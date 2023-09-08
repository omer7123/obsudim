package com.example.mypsychologist.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.ItemClientTaskBinding
import com.example.mypsychologist.domain.entity.TaskEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class ClientTaskDelegate(private val onDeleteClick: (String) -> Unit) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        ItemClientTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onDeleteClick
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as Pair<String, TaskEntity>)
    }

    override fun isOfViewType(item: DelegateItem) = item is ClientTaskDelegateItem

    class ViewHolder(
        private val binding: ItemClientTaskBinding,
        private val onDeleteClick: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Pair<String, TaskEntity>) {
            binding.apply {
                text.text = task.second.text

                if (task.second.completed) {
                    check.isVisible = true
                    text.setTextColor(itemView.context.resources.getColor(R.color.md_theme_light_primary))
                }

                delete.setOnClickListener { onDeleteClick(task.first) }
            }
        }

    }
}
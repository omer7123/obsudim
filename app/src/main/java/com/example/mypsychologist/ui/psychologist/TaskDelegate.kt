package com.example.mypsychologist.ui.psychologist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemTaskBinding
import com.example.mypsychologist.domain.entity.TaskEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.main.ClientTaskDelegateItem

class TaskDelegate(private val check: (String, String, Boolean) -> Unit) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            check
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
        private val binding: ItemTaskBinding,
        private val check: (String, String, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pair<String, TaskEntity>) {
            binding.text.text = item.second.text

            if (item.second.completed)
                binding.checkBox.isChecked = true

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                check(item.first, item.second.psychologistId, isChecked)
            }
        }
    }
}
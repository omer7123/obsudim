package com.example.mypsychologist.ui.psychologist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemTaskBinding
import com.example.mypsychologist.domain.entity.psychologistsEntity.TaskEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class TaskDelegate(private val check: (String, Boolean) -> Unit) : AdapterDelegate {

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
        (holder as ViewHolder).bind(item.content() as TaskEntity)
    }

    override fun isOfViewType(item: DelegateItem) = item is TaskFromPsychologistDelegateItem

    class ViewHolder(
        private val binding: ItemTaskBinding,
        private val check: (String, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskEntity) {
            binding.text.text = item.testTitle
//            binding.text.text = item.second.text

            if (item.isCompleted)
                binding.checkBox.isChecked = true

            binding.root.setOnClickListener {

            }
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                check(item.id, isChecked)
           }
        }
    }
}
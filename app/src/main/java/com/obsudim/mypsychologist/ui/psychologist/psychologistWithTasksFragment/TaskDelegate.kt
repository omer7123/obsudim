package com.obsudim.mypsychologist.ui.psychologist.psychologistWithTasksFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.obsudim.mypsychologist.databinding.ItemTaskBinding
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.TaskEntity
import com.obsudim.mypsychologist.ui.core.adapter.AdapterDelegate
import com.obsudim.mypsychologist.ui.core.delegateItems.DelegateItem

class TaskDelegate(
    private val check: (String, Boolean) -> Unit,
    private val onItemClickListener: (String, String, String) -> Unit
) :
    AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as TaskEntity)
    }

    override fun isOfViewType(item: DelegateItem) = item is TaskFromPsychologistDelegateItem

    inner class ViewHolder(
        private val binding: ItemTaskBinding,
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

            binding.root.setOnClickListener {
                onItemClickListener(item.testId, item.testTitle, item.testDescription)
            }
        }
    }
}
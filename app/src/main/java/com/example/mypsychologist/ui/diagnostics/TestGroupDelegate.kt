package com.example.mypsychologist.ui.diagnostics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.TestGroupItemBinding
import com.example.mypsychologist.domain.entity.TestGroupEntity
import com.example.mypsychologist.ui.core.adapter.AdapterDelegate
import com.example.mypsychologist.ui.core.delegateItems.DelegateItem

class TestGroupDelegate(
    private val onClick: (TestGroupEntity, Boolean) -> Unit
) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        TestGroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as TestGroupEntity)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is TestGroupDelegateItem

    class ViewHolder(
        private val binding: TestGroupItemBinding,
        private val onClick: (TestGroupEntity, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(group: TestGroupEntity) {
            binding.title.text = itemView.context.getString(group.titleId)
            binding.leadingIcon.setImageResource(group.iconResource)

            binding.arrow.setOnClickListener {
                onClick(group, binding.arrow.isChecked)
            }
            binding.testContainer.setOnClickListener {
                binding.arrow.isChecked = !binding.arrow.isChecked
                onClick(group, binding.arrow.isChecked)
            }
        }
    }
}
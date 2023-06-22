package com.example.mypsychologist.ui.diagnostics

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.TestGroupItemBinding
import com.example.mypsychologist.domain.entity.TestGroupEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class TestGroupDelegate(
    private val onClick: (String, Boolean) -> Unit
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
        private val onClick: (String, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(group: TestGroupEntity) {
            binding.title.text = group.title
            binding.leadingIcon.setImageResource(group.iconResource)

            binding.arrow.setOnClickListener {
                onClick(group.title, binding.arrow.isChecked)
            }
        }
    }
}
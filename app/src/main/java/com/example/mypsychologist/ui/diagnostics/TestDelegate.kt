package com.example.mypsychologist.ui.diagnostics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.TestItemBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class TestDelegate(private val onClick: (String, String, String) -> Unit) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        TestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as TestEntity)
    }

    override fun isOfViewType(item: DelegateItem) = item is TestWithoutCategoryDelegateItem

    class ViewHolder(
        private val binding: TestItemBinding,
        private val onClick: (String, String, String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(test: TestEntity) {

            binding.title.text = test.title
            binding.description.text = test.description

//            binding.title.text = itemView.context.getString(test.titleId)
//            binding.description.text = itemView.context.getString(test.shortDescriptionId)

            binding.root.setOnClickListener {
                onClick(test.testId, test.description, test.title)
            }
//            itemView.setOnClickListener { onClick(test.titleId, test.longDescriptionId) }
        }
    }
}
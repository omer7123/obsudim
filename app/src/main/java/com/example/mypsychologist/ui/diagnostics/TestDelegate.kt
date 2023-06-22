package com.example.mypsychologist.ui.diagnostics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.TestItemBinding
import com.example.mypsychologist.domain.entity.TestCardEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class TestDelegate(private val onClick: (String, String) -> Unit) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        TestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as TestCardEntity)
    }

    override fun isOfViewType(item: DelegateItem) = item is TestDelegateItem

    class ViewHolder(
        private val binding: TestItemBinding,
        private val onClick: (String, String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(test: TestCardEntity) {
            binding.title.text = test.title
            binding.description.text = test.shortDescription

            itemView.setOnClickListener { onClick(test.title, test.longDescription) }
        }
    }
}
package com.example.mypsychologist.ui.diagnostics

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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


            binding.layout.setOnClickListener {
                onClick(test.testId, test.description, test.title)
            }
            Log.d("aaaa",test.linkToPicture)
            Glide.with(itemView.context)
                .load(test.linkToPicture)
                .into(binding.imageView)
        }
    }
}
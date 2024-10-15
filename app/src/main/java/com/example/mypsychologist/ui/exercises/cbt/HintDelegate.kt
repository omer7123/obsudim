package com.example.mypsychologist.ui.exercises.cbt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemHintBinding
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class HintDelegate() : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        ItemHintBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind((item as HintDelegateItem).content())
    }

    override fun isOfViewType(item: DelegateItem) = item is HintDelegateItem

    class ViewHolder(
        private val binding: ItemHintBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Int) {
            binding.text.text = itemView.context.getString(item)
        }
    }
}
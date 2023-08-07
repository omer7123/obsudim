package com.example.mypsychologist.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.DeletableTextItemBinding
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class DeletableStringsDelegate(private val onDeleteClick: (String) -> Unit) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        DeletableTextItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onDeleteClick
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as String)
    }

    override fun isOfViewType(item: DelegateItem) = item is StringDelegateItem

    class ViewHolder(private val binding: DeletableTextItemBinding, private val onDeleteClick: (String) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(text: String) {
                binding.text.text = text

                binding.imageView.setOnClickListener { onDeleteClick(text) }
            }

    }
}
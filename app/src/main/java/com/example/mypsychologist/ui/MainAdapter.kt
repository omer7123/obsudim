package com.example.mypsychologist.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MainAdapter :
    ListAdapter<DelegateItem, RecyclerView.ViewHolder>(DelegateAdapterItemCallback()) {
    private val delegates: MutableList<AdapterDelegate> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        delegates[viewType].onCreateViewHolder(parent)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)].onBindViewHolder(holder, getItem(position), position)
    }

    fun addDelegate(delegate: AdapterDelegate) {
        delegates.add(delegate)
    }

    override fun getItemViewType(position: Int): Int =
        delegates.indexOfFirst { it.isOfViewType(currentList[position]) }

    class DelegateAdapterItemCallback : DiffUtil.ItemCallback<DelegateItem>() {
        override fun areItemsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean {
            return oldItem::class == newItem::class && oldItem.id() == newItem.id()
        }

        override fun areContentsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean {
            return oldItem::class == newItem::class && oldItem.compareToOther(newItem)
        }
    }
}
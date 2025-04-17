package com.example.mypsychologist.ui.core.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.ui.core.delegateItems.DelegateItem

interface AdapterDelegate {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int)
    fun isOfViewType(item: DelegateItem): Boolean
}
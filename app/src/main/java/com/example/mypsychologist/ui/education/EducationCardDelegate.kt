package com.example.mypsychologist.ui.education

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemEducationBinding
import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class EducationCardDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemEducationBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {

        (holder as ViewHolder).bind((item as TextCardDelegateItem).content())
    }

    override fun isOfViewType(item: DelegateItem) = item is TextCardDelegateItem


    class ViewHolder(
        private val binding: ItemEducationBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemMaterialEntity) {
            Log.e("TEXT", item.text)
            binding.textView.text = item.text
        }
    }
}
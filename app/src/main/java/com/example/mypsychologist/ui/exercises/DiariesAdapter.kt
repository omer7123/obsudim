package com.example.mypsychologist.ui.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.DiaryItemBinding
import com.example.mypsychologist.domain.entity.DiaryCard

class DiariesAdapter(private val items: List<DiaryCard>, private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<DiariesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DiaryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: DiaryItemBinding, private val onClick: (String) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(diary: DiaryCard) {
            binding.apply {
                cardTitle.text = diary.title
                cardDescription.text = diary.description
                cardImage.setImageResource(diary.imageRes)
            }
            itemView.setOnClickListener { onClick(diary.title) }
        }
    }
}
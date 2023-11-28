package com.example.mypsychologist.ui.diagnostics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemScaleResultBinding

class ScalesAdapter(private val items: List<Pair<Int, Int>>) : RecyclerView.Adapter<ScalesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemScaleResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(private val binding: ItemScaleResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Pair<Int, Int>) {
            binding.scale.text = itemView.context.getString(result.first)
            binding.result.text = result.second.toString()
        }
    }
}
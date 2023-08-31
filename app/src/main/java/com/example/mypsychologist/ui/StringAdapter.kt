package com.example.mypsychologist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.StringItemBinding

class StringAdapter(
    private val items: List<String>,
    private val onClick: ((String) -> Unit)? = null
) :
    RecyclerView.Adapter<StringAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        StringItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val binding: StringItemBinding, private val onClick: ((String) -> Unit)?) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(str: String) {
            binding.textView.text = str

            onClick?.let { onClick ->
                itemView.setOnClickListener {
                    onClick(str)
                }
            }
        }
    }
}
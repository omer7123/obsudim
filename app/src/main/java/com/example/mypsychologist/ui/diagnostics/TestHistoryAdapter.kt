package com.example.mypsychologist.ui.diagnostics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.TestHistoryItemBinding
import com.example.mypsychologist.domain.entity.TestResultEntity
import com.example.mypsychologist.toDateString
import java.util.*

class TestHistoryAdapter(private val items: List<TestResultEntity>) :
    RecyclerView.Adapter<TestHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        TestHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val binding: TestHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TestResultEntity) {
            binding.apply {
                conclusion.text = item.conclusion
                date.text = Date(item.timestamp).toDateString()
                score.text = item.score.toString()
            }
        }
    }
}
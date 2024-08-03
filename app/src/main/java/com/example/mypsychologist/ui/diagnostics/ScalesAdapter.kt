package com.example.mypsychologist.ui.diagnostics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemScaleResultBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.ConclusionOfTestEntity

class ScalesAdapter(private val items: List<ConclusionOfTestEntity>) : RecyclerView.Adapter<ScalesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemScaleResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(private val binding: ItemScaleResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: ConclusionOfTestEntity) {
            binding.scale.text = result.scaleTitle
            binding.result.text = result.score.toString()
            binding.conclusion.text = result.conclusion
        }
    }
}
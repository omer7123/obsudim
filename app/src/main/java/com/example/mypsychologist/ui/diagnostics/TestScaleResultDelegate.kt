package com.example.mypsychologist.ui.diagnostics

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemTestScaleResultBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultForHistoryEntity
import com.example.mypsychologist.extensions.toPercent
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class TestScaleResultDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemTestScaleResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as ScaleResultForHistoryEntity)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is TestScaleResultDelegateItem

    class ViewHolder(
        private val binding: ItemTestScaleResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(scale: ScaleResultForHistoryEntity) {
            binding.apply {
                scaleTitle.text = scale.scaleTitle
                resultScore.text = if(scale.score.toInt().toFloat() == scale.score ){
                    scale.score.toInt().toString()
                } else {
                    scale.score.toString()
                }
                scoreIndicator.progress = scale.score.toPercent(scale.maxScore).toInt()
                scoreIndicator.setIndicatorColor(Color.parseColor(scale.color))
            }
        }
    }
}
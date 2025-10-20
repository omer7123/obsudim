package com.obsudim.mypsychologist.ui.diagnostics.testResultFragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.databinding.ItemTestScaleResultBinding
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.ScaleResultForHistoryEntity
import com.obsudim.mypsychologist.extensions.toPercent
import com.obsudim.mypsychologist.ui.core.adapter.AdapterDelegate
import com.obsudim.mypsychologist.ui.core.delegateItems.DelegateItem

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

               // val percentOfResult = scale.score.toPercent(scale.maxScore).toInt()
                val scaleOfPercent = "${scale.score.toInt()} из ${scale.maxScore}"

                resultScore.text = scaleOfPercent
                scoreIndicator.progress = scale.score.toPercent(scale.maxScore).toInt()
                scoreIndicator.setIndicatorColor(Color.parseColor(scale.color))
                scoreIndicator.trackColor = itemView.context.getColor(R.color.md_theme_dark_onSecondaryContainer)
            }
        }
    }
}
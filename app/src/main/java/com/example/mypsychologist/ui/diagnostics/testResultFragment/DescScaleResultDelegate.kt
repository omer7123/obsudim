package com.example.mypsychologist.ui.diagnostics.testResultFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemTestScaleRecommendationBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultForHistoryEntity
import com.example.mypsychologist.ui.core.adapter.AdapterDelegate
import com.example.mypsychologist.ui.core.delegateItems.DelegateItem

class DescScaleResultDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemTestScaleRecommendationBinding.inflate(
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

    override fun isOfViewType(item: DelegateItem): Boolean = item is TestScaleConclusionDelegateItem

    class ViewHolder(
        private val binding: ItemTestScaleRecommendationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(scale: ScaleResultForHistoryEntity) {
            binding.apply {
                titleTv.text = scale.scaleTitle
                recommendationTv.text = scale.userRecommendation
            }
        }
    }
}

package com.example.mypsychologist.ui.education

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemTopicsTheoryBinding
import com.example.mypsychologist.domain.entity.educationEntity.ThemeEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class TopicsDelegate(private val onClick: (ThemeEntity) -> Unit) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemTopicsTheoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind((item as TopicDelegateItem).content())
    }

    override fun isOfViewType(item: DelegateItem) = item is TopicDelegateItem

    inner class ViewHolder(
        private val binding: ItemTopicsTheoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ThemeEntity) {
            val minutesOfTheory = "${item.maxScore} минут"
            binding.titleTv.text = item.theme
            binding.timeTv.text = minutesOfTheory
//            binding.cardDescription.text = itemView.resources.getString(
//                R.string.test_progress,
//                item.currentCard.toString(),
//                item.cardCount.toString()
//            )

//            if (item.currentCard == item.cardCount)
//                binding.card.setBackgroundResource(R.drawable.primary_card)
            binding.containerIv.setOnClickListener {
                onClick(item)
            }
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }
}
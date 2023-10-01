package com.example.mypsychologist.ui.education

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.CardViewGroupBinding
import com.example.mypsychologist.domain.entity.EducationTopicEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class TopicsDelegate(private val onClick: (Int) -> Unit) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(
            CardViewGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind((item as TopicDelegateItem).content())
    }

    override fun isOfViewType(item: DelegateItem) = item is TopicDelegateItem

    class ViewHolder(
        private val binding: CardViewGroupBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EducationTopicEntity) {

            binding.cardTitle.setText(item.titleId)
            binding.cardDescription.text = itemView.resources.getString(
                R.string.test_progress,
                item.currentCard.toString(),
                item.cardCount.toString()
            )

            if (item.currentCard == item.cardCount)
                binding.card.setBackgroundResource(R.drawable.main_card)

            itemView.setOnClickListener {
                onClick(item.titleId)
            }
        }
    }
}
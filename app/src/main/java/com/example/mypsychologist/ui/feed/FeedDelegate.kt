package com.example.mypsychologist.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemFeedMessageBinding
import com.example.mypsychologist.domain.entity.FeedItemUI
import com.example.mypsychologist.extensions.toDateString
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem
import java.util.*

class FeedDelegate(private val onLikeClick: (itemId: String, isChecked: Boolean) -> Unit) :
    AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemFeedMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onLikeClick
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind((item as FeedDelegateItem).content())
    }

    override fun isOfViewType(item: DelegateItem) = item is FeedDelegateItem

    class ViewHolder(
        private val binding: ItemFeedMessageBinding,
        private val onLikeClick: (itemId: String, isChecked: Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FeedItemUI) {
            binding.apply {
                liter.text = item.entity.author[0].uppercase()
                name.text = item.entity.author
                date.text = Date(item.entity.date).toDateString()
                text.text = item.entity.text
                likeCheckBox.text = item.entity.likeScore.toString()

                if (item.iLiked)
                    likeCheckBox.isChecked = true

                likeCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    onLikeClick(item.id, isChecked)

                    val currentLikeScore = likeCheckBox.text.toString().toInt()
                    if (isChecked)
                        likeCheckBox.text = (currentLikeScore + 1).toString()
                    else
                        likeCheckBox.text = (currentLikeScore - 1).toString()
                }
            }
        }
    }
}
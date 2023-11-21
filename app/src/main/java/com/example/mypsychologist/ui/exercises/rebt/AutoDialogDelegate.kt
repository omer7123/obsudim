package com.example.mypsychologist.ui.exercises.rebt

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.ItemAutoDialogMessageBinding
import com.example.mypsychologist.domain.entity.AutoDialogMessageEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem


class AutoDialogDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        ItemAutoDialogMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as Pair<String, AutoDialogMessageEntity>)
    }

    override fun isOfViewType(item: DelegateItem) = item is MessageDelegateItem

    class ViewHolder(private val binding: ItemAutoDialogMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Pair<String, AutoDialogMessageEntity>) {
            binding.type.text =
                itemView.context.getText(
                    if (message.second.rational) {
                        binding.text.background = itemView.context.getDrawable(R.drawable.own_message_card)
                        binding.text.setTextColor(itemView.context.resources.getColor(R.color.md_theme_light_onPrimary))
                        R.string.rational
                    }
                    else
                        R.string.irrational
                )
            binding.text.text = message.second.message
        }
    }
}
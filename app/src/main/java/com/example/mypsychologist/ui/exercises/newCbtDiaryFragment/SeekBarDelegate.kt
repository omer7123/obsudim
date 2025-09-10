package com.example.mypsychologist.ui.exercises.newCbtDiaryFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.IncludeSeekBarBinding
import com.example.mypsychologist.ui.core.adapter.AdapterDelegate
import com.example.mypsychologist.ui.core.delegateItems.DelegateItem

class SeekBarDelegate(private val onChangeValue: (Int, Int) -> Unit) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(
            IncludeSeekBarBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onChangeValue
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind((item as SliderDelegateItem).content())
    }

    override fun isOfViewType(item: DelegateItem) = item is SliderDelegateItem

    class ViewHolder(
        private val binding: IncludeSeekBarBinding,
        private val onChangeValue: (Int, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(titleId: Int) {
            binding.title.text = itemView.context.getString(titleId)

            binding.moodSb.addOnChangeListener { _, value, _ ->
                onChangeValue(titleId, value.toInt())
            }
        }
    }
}
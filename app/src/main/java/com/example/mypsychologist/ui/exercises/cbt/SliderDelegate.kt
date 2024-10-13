package com.example.mypsychologist.ui.exercises.cbt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.IncludeSeekBarBinding
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class SliderDelegate(private val onChangeValue: (Int, Int) -> Unit) : AdapterDelegate {

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
        (holder as ViewHolder).bind((item as IntDelegateItem).content())
    }

    override fun isOfViewType(item: DelegateItem) = item is IntDelegateItem

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
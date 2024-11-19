package com.example.mypsychologist.ui.exercises.cbt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.IncludeSeekBarBinding
import com.example.mypsychologist.domain.entity.InputIntExerciseEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class SliderDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(
            IncludeSeekBarBinding.inflate(LayoutInflater.from(parent.context), parent, false),
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
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: InputIntExerciseEntity) {
            binding.title.text = item.title

//            binding.moodSb.addOnChangeListener { _, value, _ ->
//                onChangeValue(titleId, value.toInt())
//            }
        }
    }
}
package com.example.mypsychologist.ui.main

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.ItemDailyExerciseBinding
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity

class DailyCardAdapter(val clickOnItem:(item: DailyExerciseEntity) -> Unit): ListAdapter<DailyExerciseEntity, DailyCardAdapter.ViewHolder>(DailyExerciseEntityDiffCallback()) {

    inner class ViewHolder(private val binding: ItemDailyExerciseBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: DailyExerciseEntity) {
            if (adapterPosition+1==itemCount) binding.lineToBottom.isVisible = false
            if(adapterPosition == 0) binding.lineToTop.isVisible = false

            if (adapterPosition != 0 && getItem(adapterPosition-1).isComplete && !item.isComplete || adapterPosition == 0 && !item.isComplete){
                binding.cardTitle.setTextAppearance(R.style.BodyMBold_White)
                binding.cardDescription.setTextAppearance(R.style.BodyM_White)
                binding.exerciseGroup.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_exercise_main)

                binding.radioBtn.buttonTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(binding.root.context, R.color.md_theme_light_primary)
                )

            }else if (item.isComplete){
                binding.radioBtn.isChecked = true
                binding.radioBtn.buttonTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(binding.root.context, R.color.md_theme_light_shadow)
                )
                binding.exerciseGroup.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_card_exercise_complete)
                binding.cardTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.md_theme_light_title_fiol))
                binding.cardDescription.setTextColor(ContextCompat.getColor(itemView.context, R.color.md_theme_light_title_fiol))

            }else {
                binding.cardTitle.setTextAppearance(R.style.BodyMBold_Black)
                binding.cardDescription.setTextAppearance(R.style.BodyM_Black)
                binding.exerciseGroup.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_card_exercise_not_complete)

                binding.radioBtn.isChecked = false
                binding.radioBtn.buttonTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(binding.root.context, R.color.md_theme_light_shadow)
                )
            }

            binding.cardTitle.text = item.title
            binding.cardDescription.text = item.shortDescription

            binding.root.setOnClickListener {
                if (adapterPosition!= 0 && getItem(adapterPosition-1).isComplete && !item.isComplete || adapterPosition == 0 && !item.isComplete){
                    clickOnItem(item)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDailyExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class DailyExerciseEntityDiffCallback : DiffUtil.ItemCallback<DailyExerciseEntity>(){
    override fun areItemsTheSame(
        oldItem: DailyExerciseEntity,
        newItem: DailyExerciseEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DailyExerciseEntity,
        newItem: DailyExerciseEntity
    ): Boolean {
        return false
    }

}

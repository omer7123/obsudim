package com.example.mypsychologist.ui.psychologist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.ItemOwnPsychologistBinding
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class OwnPsychologistDelegate(private val onMessageClick: (String) -> Unit) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemOwnPsychologistBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onMessageClick
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as ManagerEntity)
    }

    override fun isOfViewType(item: DelegateItem) = item is OwnPsychologistDelegateItem

    class ViewHolder(
        private val binding: ItemOwnPsychologistBinding,
        private val onMessageClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ManagerEntity) {
//            binding.liter.text = item.username.uppercase()
            binding.name.text = item.username
//            item.second.apply {
//                binding.liter.text = psychologistCard.name[0].uppercase()
//                binding.name.text = psychologistCard.name

//                binding.taskCount.text =
//                    itemView.context.resources.getQuantityString(R.plurals.task, taskCount, taskCount)
//            }

            binding.message.setOnClickListener {
                onMessageClick(item.id)
            }
            binding.item.setOnClickListener {
                onMessageClick(item.id)
            }
        }
    }
}
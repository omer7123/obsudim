package com.example.mypsychologist.ui.exercises.cbt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.RecordItemBinding
import com.example.mypsychologist.domain.entity.DiaryRecordEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class RecordDelegate(private val onClick: (String) -> Unit) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        RecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as DiaryRecordEntity)
    }

    override fun isOfViewType(item: DelegateItem) = item is RecordDelegateItem

    class ViewHolder(
        private val binding: RecordItemBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(record: DiaryRecordEntity) {
            binding.situation.text = record.situation

            itemView.setOnClickListener {
                onClick(record.id) }
        }
    }
}
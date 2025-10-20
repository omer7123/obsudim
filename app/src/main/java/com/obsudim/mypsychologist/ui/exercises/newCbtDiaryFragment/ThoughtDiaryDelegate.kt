package com.obsudim.mypsychologist.ui.exercises.newCbtDiaryFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.obsudim.mypsychologist.databinding.IncludeEditTextBinding
import com.obsudim.mypsychologist.domain.entity.ThoughtDiaryItemEntity
import com.obsudim.mypsychologist.ui.core.adapter.AdapterDelegate
import com.obsudim.mypsychologist.ui.core.delegateItems.DelegateItem

class ThoughtDiaryDelegate(
  //  private val onHelpClick: (Int, Int) -> Unit
) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        IncludeEditTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
     //   onHelpClick
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind((item as ThoughtDiaryDelegateItem).content())
    }

    override fun isOfViewType(item: DelegateItem) = item is ThoughtDiaryDelegateItem

    class ViewHolder(
        private val binding: IncludeEditTextBinding,
  //      private val onHelpClick: (Int, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ThoughtDiaryItemEntity) {
            binding.apply {
             /*   itemView.context.apply {
                    inputLayout.hint = getString(item.titleId)
                    inputLayout.helperText = getString(item.hintId)

                    if (item.isNotCorrect)
                        inputLayout.error = getString(R.string.necessary_to_fill)
                } */

                editText.addTextChangedListener {
                    item.saveFunction(it.toString())
                 //   inputLayout.error = null
                }

                editText.setHint(itemView.context.getString(item.hintId))
            }
        }
    }
}
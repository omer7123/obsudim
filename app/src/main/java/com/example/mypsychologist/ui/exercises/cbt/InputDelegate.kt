package com.example.mypsychologist.ui.exercises.cbt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.IncludeEditTextBinding
import com.example.mypsychologist.domain.entity.InputItemEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem

class InputDelegate(
    private val onHelpClick: ((Int, Int) -> Unit)? = null
) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        IncludeEditTextBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onHelpClick
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind((item as InputDelegateItem).content())
    }

    override fun isOfViewType(item: DelegateItem) = item is InputDelegateItem


    class ViewHolder(
        private val binding: IncludeEditTextBinding,
        private val onHelpClick: ((Int, Int) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: InputItemEntity) {
            binding.apply {
                item.hintId?.let { id ->
                    editText.hint = itemView.context.getString(id)
                }
                if (item.isNotCorrect)
                    editText.hint = itemView.context.getString(R.string.necessary_to_fill)

                editText.addTextChangedListener {
                    item.saveFunction(it.toString())
                }

                editText.setText(item.text)

             /*   itemView.context.apply {
                    inputLayout.hint = getString(item.titleId)

                    item.hintId?.let { hintIdNotNull ->
                        inputLayout.helperText = getString(hintIdNotNull)
                        inputLayout.setEndIconOnClickListener {
                            onHelpClick!!(item.titleId, hintIdNotNull)
                        }
                        inputLayout.endIconDrawable =
                            AppCompatResources.getDrawable(itemView.context, R.drawable.ic_help)
                    }


                    if (item.isNotCorrect)
                        inputLayout.error = getString(R.string.necessary_to_fill)
                }

                field.addTextChangedListener {
                    item.saveFunction(it.toString())
                    inputLayout.error = null
                }

                field.setText(item.text)*/
            }
        }
    }
}
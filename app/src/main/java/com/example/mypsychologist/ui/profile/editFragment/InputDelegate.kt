package com.example.mypsychologist.ui.profile.editFragment

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.IncludeEditTextBinding
import com.example.mypsychologist.domain.entity.InputItemEntity
import com.example.mypsychologist.domain.entity.InputItemExerciseEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultEntity
import com.example.mypsychologist.ui.core.adapter.AdapterDelegate
import com.example.mypsychologist.ui.core.delegateItems.DelegateItem
import com.example.mypsychologist.ui.core.delegateItems.InputDelegateItem
import com.example.mypsychologist.ui.core.delegateItems.InputExerciseDelegateItem

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

class InputExerciseDelegate(
    private val itemCount: Int,
    private val onHelpClick: ((Int, Int) -> Unit)? = null
) : AdapterDelegate {
    private val mDataSet = HashMap<Int, String>()
    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        IncludeEditTextBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onHelpClick
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind((item as InputExerciseDelegateItem).content())
    }

    override fun isOfViewType(item: DelegateItem) = item is InputExerciseDelegateItem


    inner class ViewHolder(
        private val binding: IncludeEditTextBinding,
        private val onHelpClick: ((Int, Int) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: InputItemExerciseEntity) {
            if (adapterPosition != itemCount-2)
                binding.editText.imeOptions = EditorInfo.IME_ACTION_NEXT
            else
                binding.editText.imeOptions = EditorInfo.IME_ACTION_DONE

            binding.editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding.editText.imeOptions = binding.editText.imeOptions

            binding.apply {
                editText.hint = item.titleId

                if (item.isNotCorrect)
                    editLayout.error = itemView.context.getString(R.string.necessary_to_fill)

                editText.setText(item.text)

                editText.addTextChangedListener {
                    item.saveFunction(ExerciseResultEntity(item.id, it.toString()))
                }

            }
        }
    }
}
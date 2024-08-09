package com.example.mypsychologist.ui.diagnostics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemTestDateSwitchBinding
import com.example.mypsychologist.databinding.TestHistoryItemBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity

class TestDateSwitchAdapter(
    private val items: List<TestResultsGetEntity>,
    private val onSwitch: (testResultId: String, isChecked: Boolean) -> Unit,
    private val onGoButtonClick: (testResultId: String) -> Unit
) : RecyclerView.Adapter<TestDateSwitchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemTestDateSwitchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onSwitch, onGoButtonClick
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(
        private val binding: ItemTestDateSwitchBinding,
        private val onSwitch: (testResultId: String, isChecked: Boolean) -> Unit,
        private val onGoButtonClick: (testResultId: String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TestResultsGetEntity) {
            binding.apply {
                date.text = item.datetime

                switchTest.setOnCheckedChangeListener { _, isChecked ->
                    onSwitch(item.testResultId, isChecked)
                }

                goToButton.setOnClickListener {
                    onGoButtonClick(item.testResultId)
                }
            }
        }
    }
}
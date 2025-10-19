package com.obsudim.mypsychologist.ui.diagnostics.historyTestFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.obsudim.mypsychologist.databinding.ItemTestDateSwitchBinding
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class TestDateSwitchAdapter(
    val onSwitch: (testResultId: String, isChecked: Boolean) -> Unit,
    val onGoButtonClick: (testResultId: String) -> Unit,
    private val selectedItems: List<String> = emptyList(),
) : ListAdapter<TestResultsGetEntity, TestDateSwitchAdapter.ViewHolder>(TestResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemTestDateSwitchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemTestDateSwitchBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TestResultsGetEntity) {
            binding.apply {
                date.text = convertToDate(item.datetime)

                switchTest.isChecked = selectedItems.contains(item.testResultId)
                switchTest.setOnCheckedChangeListener { _, isChecked ->
                    onSwitch(item.testResultId, isChecked)
                }

                goToButton.setOnClickListener {
                    onGoButtonClick(item.testResultId)
                }
            }
        }

        @SuppressLint("NewApi")
        private fun convertToDate(date: String): String {
            val utcDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

            val zoneId = ZoneId.systemDefault()
            val utcZonedDateTime = utcDateTime.atZone(ZoneId.of("UTC"))
            val localZonedDateTime = utcZonedDateTime.withZoneSameInstant(zoneId)

            val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

            val formattedDate = localZonedDateTime.format(dateFormatter)
            val formattedTime = localZonedDateTime.format(timeFormatter)

            return "$formattedDate, $formattedTime"
        }
    }
}

class TestResultDiffCallback : DiffUtil.ItemCallback<TestResultsGetEntity>() {
    override fun areItemsTheSame(
        oldItem: TestResultsGetEntity,
        newItem: TestResultsGetEntity
    ): Boolean {
        return oldItem.testResultId == newItem.testResultId
    }

    override fun areContentsTheSame(
        oldItem: TestResultsGetEntity,
        newItem: TestResultsGetEntity
    ): Boolean {
        return oldItem == newItem
    }

}
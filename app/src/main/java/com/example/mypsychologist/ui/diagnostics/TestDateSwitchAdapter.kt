package com.example.mypsychologist.ui.diagnostics

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.ItemTestDateSwitchBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class TestDateSwitchAdapter(
    private val items: List<TestResultsGetEntity>,
    private val onSwitch: (testResultId: String, isChecked: Boolean) -> Unit,
    private val onGoButtonClick: (testResultId: String) -> Unit
) : RecyclerView.Adapter<TestDateSwitchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemTestDateSwitchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(
        private val binding: ItemTestDateSwitchBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TestResultsGetEntity) {
            binding.apply {
                date.text = convertToDate(item.datetime)

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
            val dateTime =
                LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

            val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

            val formattedDate = dateTime.format(dateFormatter)
            val formattedTime = dateTime.format(timeFormatter)

            return "$formattedDate, $formattedTime"

        }
    }
}
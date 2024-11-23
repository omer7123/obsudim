package com.example.mypsychologist.ui.exercises

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.databinding.RecordItemBinding
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultFromAPIEntity
import com.example.mypsychologist.ui.AdapterDelegate
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.exercises.cbt.exerciseResultsFragment.ExerciseResultDelegateItem
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ExerciseResultsDelegate(private val onClick: (String) -> Unit) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        RecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as ExerciseResultFromAPIEntity)
    }

    override fun isOfViewType(item: DelegateItem) = item is ExerciseResultDelegateItem

    class ViewHolder(
        private val binding: RecordItemBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(record: ExerciseResultFromAPIEntity) {
            binding.situation.text = convertToUserTimeZone(record.date)

            itemView.setOnClickListener {
                onClick(record.completedExerciseId) }
        }

        private fun convertToUserTimeZone(isoDate: String): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Для API 26 и выше
                val utcDateTime = LocalDateTime.parse(
                    isoDate,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSSSSS][.SSS]")
                ).atZone(ZoneId.of("UTC"))

                val userZoneId = ZoneId.systemDefault()
                val userDateTime = utcDateTime.withZoneSameInstant(userZoneId)

                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").format(userDateTime)
            } else {
                // Для старых версий Android с ThreeTenABP
                val utcDateTime = org.threeten.bp.LocalDateTime.parse(
                    isoDate,
                    org.threeten.bp.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSSSSS][.SSS]")
                ).atZone(org.threeten.bp.ZoneId.of("UTC"))

                val userZoneId = org.threeten.bp.ZoneId.systemDefault()
                val userDateTime = utcDateTime.withZoneSameInstant(userZoneId)

                org.threeten.bp.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").format(userDateTime)
            }
        }
    }
}
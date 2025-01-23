package com.example.mypsychologist.extensions

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Date.convertDateToString(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = this.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        localDate.format(formatter)
    } else {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formatter.format(date)
    }
}

fun String.convertToTimeOnly(): String {

    val dateTime = LocalDateTime.parse(this)

    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return dateTime.format(formatter)
}
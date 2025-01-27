package com.example.mypsychologist.extensions

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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

fun String.convertToTimeOnlyDateJava(): String {
    // Парсим строку в ZonedDateTime с учетом UTC ('Z')
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ENGLISH)
    val utcDateTime = ZonedDateTime.parse(this, formatter)

    // Преобразуем в локальное время
    val localDateTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault())

    // Форматируем в строку "часы:минуты"
    return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}
fun Date.convertToISO8601(): String {
    // Формат входящей строки
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    inputFormat.timeZone = TimeZone.getTimeZone("GMT+03:00")

    // Формат для результата (ISO 8601)
    val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    outputFormat.timeZone = TimeZone.getTimeZone("UTC") // Конвертация в UTC

    // Разбираем строку в Date и форматируем в ISO 8601
    val date = inputFormat.parse(this.toString())
    return outputFormat.format(date!!)
}

infix fun Date.isSameDay(selectedDate: Date): Boolean {
    val calendarSelected = Calendar.getInstance().apply {
        time = selectedDate
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    val calendarCurrent = Calendar.getInstance().apply {
        time = this.time
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    return calendarSelected.time == calendarCurrent.time
}
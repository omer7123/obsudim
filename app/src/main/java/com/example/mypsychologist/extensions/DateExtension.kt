package com.example.mypsychologist.extensions

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
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

fun String.convertLondonTimeToDeviceTime(): String {
    // Форматы для парсинга
    val formatterWithMicroSec = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val formatterWithoutMicroSec = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    return try {
        // Пробуем парсить с микросекундами (если удастся, значит это UTC)
        val utcDateTime = LocalDateTime.parse(this, formatterWithMicroSec)
        val utcZonedDateTime = utcDateTime.atZone(ZoneOffset.UTC) // Указываем, что это UTC

        // Конвертируем в локальное время устройства
        val deviceTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault())

        // Форматируем в "HH:mm"
        deviceTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: DateTimeParseException) {
        try {
            // Если формат без микросекунд → просто возвращаем "00:00"
            LocalDateTime.parse(this, formatterWithoutMicroSec)
            "00:00"
        } catch (e: DateTimeParseException) {
            throw IllegalArgumentException("Неверный формат времени: $this")
        }
    }
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
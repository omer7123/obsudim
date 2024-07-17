package com.example.mypsychologist.extensions

import com.example.mypsychologist.data.model.TaskIdModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toYears(): Long =
    this / MILLIS_IN_YEAR

const val MILLIS_IN_YEAR = 31536000000

fun Date.toDateString(): String {
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return format.format(this)
}

fun List<Int>.sum(elements: Array<Int>) = run {
    var sum = 0

    elements.forEach {index ->
        sum += this[index]
    }

    sum
}

package com.example.mypsychologist.extensions

import android.content.Context

fun Float.toPercent(maxValue: Float):Float {
    return this / maxValue * 100
}
fun Int.toDp(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()
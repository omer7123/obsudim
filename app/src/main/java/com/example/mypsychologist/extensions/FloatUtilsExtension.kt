package com.example.mypsychologist.extensions

fun Float.toPercent(maxValue: Float):Float {
    return this / maxValue * 100
}
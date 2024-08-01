package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.mypsychologist.R
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class CustomRadarChart(context: Context, attrs: AttributeSet?) : RadarChart(context, attrs) {

    private val circlePaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.md_theme_dark_onPrimaryIcon)
        style = Paint.Style.STROKE
        strokeWidth = 12f
    }

    private val fillPaint = Paint().apply {
        color =
            ContextCompat.getColor(
                context,
                R.color.md_theme_light_primaryContainer
            ) // Цвет заливки окружностей
        style = Paint.Style.STROKE
        alpha = 100 // Прозрачность заливки (0-255)
    }

    private val dataSet: RadarDataSet = RadarDataSet(emptyList(), "Результаты").apply {
        color = ContextCompat.getColor(context, R.color.md_theme_dark_onSecondPrimaryIcon) // Цвет линии
        fillColor = ContextCompat.getColor(context, R.color.md_theme_dark_onSecondPrimaryIcon_with_alpha) // Цвет заливки
        setDrawFilled(true) // Заполнение области
        lineWidth = 4f // Толщина линии
        valueTextColor = ContextCompat.getColor(context, R.color.md_theme_dark_onSecondPrimaryIcon) // Цвет текста значений
        valueTextSize = 14f // Размер текста значений
        setDrawHighlightCircleEnabled(true) // Включение кружков на вершинах
        setDrawHighlightIndicators(false) // Отключение индикаторов выделения
        setDrawValues(false) // Отключение значений на графике
    }

    init {
        configureChart()
    }

    private fun configureChart() {
        // Настройка осей
        val labels = arrayOf("Тревога", "Выгорание", "РПП")

        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.textSize = 14f
        xAxis.typeface = Typeface.DEFAULT_BOLD
        xAxis.textColor = ContextCompat.getColor(context, R.color.md_theme_dark_onSecondPrimaryIcon)
        xAxis.xOffset = 100f
        xAxis.yOffset = 100f

        yAxis.setLabelCount(5, true)
        yAxis.textSize = 14f
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 50f

        yAxis.setDrawLabels(false) // Убираем метки

        // Убираем фоновую сетку и оси координат
        webLineWidth = 1f // Убираем линии сетки
        webColor = ContextCompat.getColor(context, R.color.md_theme_dark_onSecondPrimaryIcon) // Делаем линии сетки прозрачными
        webColorInner = ContextCompat.getColor(
            context,
            R.color.md_theme_dark_onSecondPrimaryIcon
        ) // Делаем внутренние линии сетки прозрачными
        webAlpha = 0 // Делаем линии сетки полностью прозрачными

        setDrawWeb(false) // Отключаем стандартную сетку
        isRotationEnabled = false
        rotationAngle = 30f
        description.isEnabled = false
        legend.isEnabled = false

        invalidate() // Перерисовываем график
    }

    // Метод для обновления данных
    fun updateData(entries: List<Entry>, labels: List<String>, maxValue: Float) {
        dataSet.values = entries as MutableList<RadarEntry>?
        val radarData = RadarData(dataSet)
        this.data = radarData
        if (labels.size == 3) rotationAngle = 30f
        if (labels.size == 4) rotationAngle = 45f
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        yAxis.axisMaximum = maxValue

        invalidate() // Перерисовываем график для отображения новых данных
    }

    override fun onDraw(canvas: Canvas) {

        canvas.let { canvasNotNull ->
            val count = 4
            val radiusStep = radius / count
            for (i in 1..count) {

                fillPaint.strokeWidth = radiusStep * (i + 1) - radiusStep * i

                when (i) {
                    1 -> renderFirstCircle(canvasNotNull, radiusStep, fillPaint)
                    2, 3 -> renderOtherCircle(canvasNotNull, radiusStep, fillPaint, i)
                }

                canvasNotNull.drawCircle(
                    viewPortHandler.contentCenter.x,
                    viewPortHandler.contentCenter.y,
                    radiusStep * i,
                    circlePaint
                )
            }
        }
        super.onDraw(canvas)
    }

    private fun renderOtherCircle(canvas: Canvas, radiusStep: Float, fillPaint: Paint, step: Int) {
        canvas.drawCircle(
            viewPortHandler.contentCenter.x,
            viewPortHandler.contentCenter.y,
            radiusStep * step - 38,
            fillPaint
        )
    }

    private fun renderFirstCircle(it: Canvas, radiusStep: Float, fillPaint: Paint) {
        it.drawCircle(
            viewPortHandler.contentCenter.x,
            viewPortHandler.contentCenter.y,
            radiusStep / 2,
            fillPaint
        )
    }
}
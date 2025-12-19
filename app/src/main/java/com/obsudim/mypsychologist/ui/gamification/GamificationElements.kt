package com.obsudim.mypsychologist.ui.gamification

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.obsudim.mypsychologist.ui.theme.AppTheme
import java.util.Calendar

@Composable
fun ProgressBarScore(
    currentValue: Int,
    maxValue: Int = 40,
    modifier: Modifier = Modifier,
    minLeftWidth: Dp = 64.dp
) {
    val progress = if (maxValue > 0) currentValue.toFloat() / maxValue.toFloat() else 0f
    val clampedProgress = progress.coerceIn(0f, 1f)

    val backgroundColor = when {
        currentValue >= 30 -> Color(0xFFC8F1E7)
        currentValue >= 10 -> Color(0xFFFFF5CA)
        else -> Color(0xFFFFDDDD)
    }

    val progressColor = when {
        currentValue >= 30 -> Color(0xFF117C61)
        currentValue >= 10 -> Color(0xFFFFDD1B)
        else -> Color(0xFFDD4261)
    }

    val textColor = AppTheme.colors.primaryTextInvert

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(backgroundColor)
        )

        Box(
            modifier = Modifier
                .widthIn(min = minLeftWidth)
                .fillMaxWidth(fraction = clampedProgress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(20.dp))
                .background(progressColor)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .widthIn(min = minLeftWidth)
                    .fillMaxWidth(fraction = clampedProgress)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$currentValue",
                    color = textColor,
                    style = AppTheme.typography.titleCygreFont,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }

            Box(
                modifier = Modifier
                    .widthIn(min = 64.dp)
                    .fillMaxWidth(fraction = 1f - clampedProgress)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$maxValue",
                    color = textColor,
                    style = AppTheme.typography.titleCygreFont,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun Graph(scores: List<Int>) {

    val calendar = Calendar.getInstance()

    val lastSevenDays = (6 downTo 0).map { offset ->
        val dayCalendar = Calendar.getInstance().apply {
            timeInMillis = calendar.timeInMillis
            add(Calendar.DAY_OF_YEAR, -offset)
        }
        dayCalendar
    }

    val days = lastSevenDays.map { dayCalendar ->
        when (dayCalendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "ПН"
            Calendar.TUESDAY -> "ВТ"
            Calendar.WEDNESDAY -> "СР"
            Calendar.THURSDAY -> "ЧТ"
            Calendar.FRIDAY -> "ПТ"
            Calendar.SATURDAY -> "СБ"
            Calendar.SUNDAY -> "ВС"
            else -> ""
        }
    }

    Box(
        modifier = Modifier
            .width(380.dp)
            .height(148.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(color = AppTheme.colors.tertiaryBackground)
            .padding(16.dp)
    ) {

        Canvas(
            modifier = Modifier
                .padding(start = 30.dp, top = 15.dp)
                .width(318.dp)
                .height(69.dp)
        ) {

            val y40 = 0f
            val y20 = size.height / 2f
            val y0 = size.height

            drawLine(
                color = Color.White,
                start = Offset(0f, y40),
                end = Offset(size.width, y40),
                strokeWidth = 5f
            )

            drawLine(
                color = Color.White,
                start = Offset(0f, y20),
                end = Offset(size.width, y20),
                strokeWidth = 5f
            )

            drawLine(
                color = Color.White,
                start = Offset(0f, y0),
                end = Offset(size.width, y0),
                strokeWidth = 5f
            )
        }

        Canvas (
            modifier = Modifier
                .padding(start = 40.dp, top = 15.dp)
                .width(280.dp)
                .height(69.dp)
        ) {
            val stepX = size.width / 6f
            val maxScore = 40f

            val points = scores.mapIndexed { index, s ->
                Offset(
                    x = stepX * index,
                    y = size.height - (s / maxScore) * size.height
                )
            }

            points.forEach { point ->
                drawLine(
                    color = Color(0xFF97ACFF),
                    start = Offset(point.x, size.height),
                    end = Offset(point.x, point.y),
                    strokeWidth = 2f
                )
            }

            val areaPath = Path().apply {
                moveTo(points.first().x, size.height)
                points.forEach { lineTo(it.x, it.y) }
                lineTo(points.last().x, size.height)
                close()
            }
            drawPath(areaPath, Color(0xFF3555D4).copy(alpha = 0.3f))

            val linePath = Path().apply {
                moveTo(points.first().x, points.first().y)
                points.forEach { lineTo(it.x, it.y) }
            }
            drawPath(
                linePath,
                Color(0xFF3555D4),
                style = Stroke(width = 6f)
            )

            points.forEach { p ->
                drawCircle(
                    color = Color(0xFF0C2A9F),
                    radius = 8f,
                    center = p
                )
            }
        }


        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 30.dp)
                .width(318.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEach { d ->
                Text(
                    text = d,
                    style = AppTheme.typography.bodyL,
                    color = AppTheme.colors.primaryText)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 26.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(40, 20, 0).forEach { value ->
                Text(
                    text = "$value",
                    style = AppTheme.typography.bodyL,
                    color = AppTheme.colors.primaryText)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun HorizontalScaleBarLinearPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressBarScore(
                currentValue = 0,
                maxValue = 40,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ProgressBarScore(
                currentValue = 9,
                maxValue = 40,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ProgressBarScore(
                currentValue = 10,
                maxValue = 40,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ProgressBarScore(
                currentValue = 29,
                maxValue = 40,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ProgressBarScore(
                currentValue = 30,
                maxValue = 40,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ProgressBarScore(
                currentValue = 40,
                maxValue = 40,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun GraphPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Graph(
                scores = listOf(0, 20, 30, 10, 30, 20, 40)
            )
        }
    }
}


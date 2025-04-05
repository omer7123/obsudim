package com.example.mypsychologist.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.diaryEntity.CalendarEntity
import com.example.mypsychologist.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// Функции форматирования
private fun Date.formatToCalendarDay(): String =
    SimpleDateFormat("d", Locale.getDefault()).format(this)

private fun Date.formatToMonthString(): String =
    SimpleDateFormat("LLLL", Locale.getDefault()).format(this).replaceFirstChar {
        it.uppercase()
    }

fun Date.formatToMonthStringInf(): String =
    SimpleDateFormat("MMMM", Locale.getDefault()).format(this).replaceFirstChar {
        it.uppercase()
    }
fun Date.formatToDayString(): String =
    SimpleDateFormat("dd", Locale.getDefault()).format(this)


private fun Int.getDayOfWeek3Letters(): String? =
    Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, this@getDayOfWeek3Letters) }
        .getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())

// Компонент: Ячейка дня
@Composable
private fun CalendarCell(
    date: Date,
    signal: Boolean,
    hasDiary: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = date.formatToCalendarDay()
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )

    ) {
        if (signal) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(
                        shape = RoundedCornerShape(12.dp),
                        color = AppTheme.colors.secondaryBackground
                    )
                    .size(36.dp)
            )
        }else if(hasDiary){
            Box(modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 30.dp)
                .size(width = 12.dp, height = 4.dp)
                .background(
                    color = AppTheme.colors.fiveBackground,
                    shape = RoundedCornerShape(12.dp)
                )
            )
        }
        Text(
            text = text,
            color = if (signal) AppTheme.colors.primaryBackground else AppTheme.colors.primaryTextInvert,
            style = AppTheme.typography.bodyXL,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

// Компонент: Ячейка дня недели
@Composable
private fun WeekdayCell(weekday: Int, modifier: Modifier = Modifier) {
    val text = weekday.getDayOfWeek3Letters()
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text.orEmpty().uppercase(),
            style = AppTheme.typography.bodyXLBold,
            color = AppTheme.colors.primaryTextInvert,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// Сетка календаря
@Composable
private fun CalendarGrid(
    date: List<CalendarEntity>,
    onClick: (Date) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier,
) {
    val weekdayFirstDay = Calendar.getInstance().apply { time = date.first().date }.get(Calendar.DAY_OF_WEEK)
    val weekdays = getWeekDays(startFromSunday)
    CalendarCustomLayout(modifier = modifier) {
        weekdays.forEach {
            WeekdayCell(weekday = it)
        }
        repeat(if (!startFromSunday) weekdayFirstDay - 2 else weekdayFirstDay - 1) {
            Spacer(modifier = Modifier)
        }
        date.forEach {
            CalendarCell(date = it.date, signal = it.signal, hasDiary = it.diary, onClick = { onClick(it.date) })
        }
    }
}

// Получение дней недели
fun getWeekDays(startFromSunday: Boolean): List<Int> {
    val list = (1..7).toList()
    return if (startFromSunday) list else list.drop(1) + list.take(1)
}

// Кастомный Layout
@Composable
private fun CalendarCustomLayout(
    modifier: Modifier = Modifier,
    horizontalGapDp: Dp = 2.dp,
    verticalGapDp: Dp = (-10).dp,
    content: @Composable () -> Unit,
) {
    val horizontalGap = with(LocalDensity.current) { horizontalGapDp.roundToPx() }
    val verticalGap = with(LocalDensity.current) { verticalGapDp.roundToPx() }

    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val totalWidthWithoutGap = constraints.maxWidth - (horizontalGap * 6)
        val singleWidth = totalWidthWithoutGap / 7

        var currentX = 0
        var currentY = 0
        val positions = measurables.map {
            val placeable = it.measure(constraints.copy(maxHeight = singleWidth, maxWidth = singleWidth))
            val position = Pair(currentX, currentY)
            if (currentX + singleWidth + horizontalGap > totalWidthWithoutGap) {
                currentX = 0
                currentY += singleWidth + verticalGap
            } else {
                currentX += singleWidth + horizontalGap
            }
            position to placeable
        }

        layout(
            width = constraints.maxWidth,
            height = currentY + singleWidth + verticalGap
        ) {
            positions.forEach { (position, placeable) ->
                placeable.placeRelative(x = position.first, y = position.second)
            }
        }
    }
}

// Компонент: Отображение календаря
@Composable
fun CalendarView(
    month: Date,
    years: String,
    date: List<CalendarEntity>,
    displayNext: Boolean,
    displayPrev: Boolean,
    onClickNext: () -> Unit,
    onClickPrev: () -> Unit,
    onClick: (Date) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(AppTheme.colors.primaryBackground)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp, start = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = month.formatToMonthString() + " " + years,
                style = AppTheme.typography.titleXS,
                color = AppTheme.colors.primaryTextInvert,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (displayPrev)
                IconButton(
                    onClick = onClickPrev,
                )
                {
                    Icon(
                        painterResource(id = R.drawable.ic_arrow_left_calendar),
                        contentDescription = "Предыдущий месяц",
                        tint = AppTheme.colors.screenBackground
                    )
                }

            if (displayNext)
                IconButton(onClick = {onClickNext()}) {
                    Icon(
                        painterResource(id = R.drawable.ic_arrow_right_calendar),
                        contentDescription = "Следующий месяц",
                        tint = AppTheme.colors.screenBackground
                    )
                }
        }

        if (date.isNotEmpty()) {
            CalendarGrid(
                date = date,
                onClick = onClick,
                startFromSunday = startFromSunday,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp)
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

// Пример для Preview
@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    AppTheme {

        val calendar = Calendar.getInstance()
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
//        val dates = (1..daysInMonth).map {
//            calendar.set(Calendar.DAY_OF_MONTH, it)
//            Pair(calendar.time, it % 5 == 0) // Пример: сигналы для каждых 5 дней
//        }
        var dates = (1..daysInMonth).map {
            calendar.set(Calendar.DAY_OF_MONTH, it)
            calendar.time
        }

        val newDates = dates.map {
            CalendarEntity(
                date = it,
                signal = false,
                diary = true
            )
        }
        CalendarView(
            month = Date(),
            years = "2024",
            date = newDates,
            displayNext = true,
            displayPrev = true,
            onClickNext = {},
            onClickPrev = {},
            onClick = {},
            startFromSunday = false,
            modifier = Modifier.fillMaxSize()
        )
    }
}
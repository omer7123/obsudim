package com.obsudim.mypsychologist.data.repository.notification

import com.obsudim.mypsychologist.domain.entity.NotificationContent
import com.obsudim.mypsychologist.domain.repository.NotificationContentProvider
import javax.inject.Inject

class NotificationContentProviderImpl @Inject constructor() : NotificationContentProvider {
    val contents = listOf(
        NotificationContent(
            title = "Привет! Загляните на 10 минут?",
            text = "Ваше ежедневное пространство для заботы о себе уже готово."
        ),
        NotificationContent(
            title = "Время для короткой перезагрузки.",
            text = "Глубокий вдох, выдох — и заходите в приложение. Вас ждет что-то полезное."
        ),
        NotificationContent(
            title = "Не забудьте о себе.",
            text = "Сегодня ещё есть время для небольшой психологической тренировки. Давайте начнём?"
        ),
        NotificationContent(
            title = "Время для заботы о себе.",
            text = "Выполните упражнение — это поможет снять напряжение и настроиться на позитивный лад."
        ),
        NotificationContent(
            title = "☀ Привет!",
            text = "10 минут для себя сегодня — и день станет чуточку легче."
        ),
        NotificationContent(
            title = "\uD83C\uDF3F Пора сделать паузу?",
            text = "Дневник эмоций ждет."
        )
    )

    override fun getNotification(): NotificationContent {
        return contents.random()
    }
}
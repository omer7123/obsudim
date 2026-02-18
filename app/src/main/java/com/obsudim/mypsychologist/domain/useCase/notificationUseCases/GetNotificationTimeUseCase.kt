package com.obsudim.mypsychologist.domain.useCase.notificationUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.repository.NotificationPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationTimeUseCase @Inject constructor(
    private val repository: NotificationPreferencesRepository
) {
    operator fun invoke(): Flow<Resource<String>> = repository.getNotificationTime()
}
package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class ChangeBirthdayUseCase @Inject constructor(private val repository: ProfileRepository) {
    operator fun invoke(it: Long) =
        repository.changeBirthday(it)
}
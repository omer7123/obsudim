package com.obsudim.mypsychologist.domain.useCase.authenticationUseCases

import com.obsudim.mypsychologist.domain.entity.authenticationEntity.ResetPasswordEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(resetPasswordEntity: ResetPasswordEntity) =
        repository.resetPassword(resetPasswordEntity)
}
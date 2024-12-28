package com.example.mypsychologist.domain.useCase.authenticationUseCases

import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class AuthWithDataUserUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(authModel: AuthModel) =
        repository.authOld(authModel)
}
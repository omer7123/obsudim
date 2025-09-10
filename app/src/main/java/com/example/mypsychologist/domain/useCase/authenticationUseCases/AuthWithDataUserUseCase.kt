package com.example.mypsychologist.domain.useCase.authenticationUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.domain.entity.authenticationEntity.Tokens
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class AuthWithDataUserUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(authModel: AuthModel): Resource<Tokens> =
        repository.authOld(authModel)
}
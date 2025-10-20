package com.obsudim.mypsychologist.domain.useCase.authenticationUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.AuthModel
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.Tokens
import com.obsudim.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class AuthWithDataUserUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(authModel: AuthModel): Resource<Tokens> =
        repository.authOld(authModel)
}
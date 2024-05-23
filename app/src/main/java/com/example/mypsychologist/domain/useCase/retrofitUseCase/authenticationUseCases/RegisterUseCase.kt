package com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases

import com.example.mypsychologist.domain.entity.authenticationEntity.Register
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(val repository: AuthenticationRepository) {
    suspend operator fun invoke(register: Register) =
        repository.register(register)

}
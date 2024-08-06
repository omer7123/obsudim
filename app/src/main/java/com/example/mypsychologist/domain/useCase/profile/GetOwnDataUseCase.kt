package com.example.mypsychologist.domain.useCase.profile

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class GetOwnDataUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(): Resource<ClientInfoEntity> =
        repository.getOwnInfo()

}
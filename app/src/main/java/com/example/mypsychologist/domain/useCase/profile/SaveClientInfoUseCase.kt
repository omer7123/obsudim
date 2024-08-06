package com.example.mypsychologist.domain.useCase.profile

import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveClientInfoUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(infoEntity: ClientInfoEntity) =
        repository.saveClient(infoEntity)
}

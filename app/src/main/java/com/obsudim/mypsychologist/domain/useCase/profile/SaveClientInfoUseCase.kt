package com.obsudim.mypsychologist.domain.useCase.profile

import com.obsudim.mypsychologist.domain.entity.ClientInfoEntity
import com.obsudim.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveClientInfoUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(infoEntity: ClientInfoEntity) =
        repository.saveClient(infoEntity)
}

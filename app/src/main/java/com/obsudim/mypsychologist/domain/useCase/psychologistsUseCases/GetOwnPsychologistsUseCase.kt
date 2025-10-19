package com.obsudim.mypsychologist.domain.useCase.psychologistsUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class GetOwnPsychologistsUseCase @Inject constructor(private val repository: PsychologistRepository) {
    suspend operator fun invoke(): Resource<List<ManagerEntity>> =
        repository.getOwnPsychologists()
}
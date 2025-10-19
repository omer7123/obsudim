package com.obsudim.mypsychologist.domain.useCase.psychologistsUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.MyPsychologistEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class GetYourPsychologistsUseCase @Inject constructor(private val repository: PsychologistRepository) {

    suspend operator fun invoke(): Resource<List<MyPsychologistEntity>> =
        repository.getYourPsychologists()
}
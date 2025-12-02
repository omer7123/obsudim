package com.obsudim.mypsychologist.domain.useCase.modelDepressionUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.ModelDepressionPostReqEntity
import com.obsudim.mypsychologist.domain.entity.ModelDepressionRespEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.ModelDeprRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveNoteModelUseCase @Inject constructor(private val repository: ModelDeprRepository){
    suspend operator fun invoke(note: ModelDepressionPostReqEntity): Flow<Resource<ModelDepressionRespEntity>> = repository.postNote(note)
}
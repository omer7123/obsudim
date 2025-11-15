package com.obsudim.mypsychologist.data.remote.modelDepression

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.ModelDepressionPostReq
import com.obsudim.mypsychologist.data.model.ModelDepressionResp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ModelDeprDataSourceImpl @Inject constructor(private val api: ModelService): ModelDeprDataSource, BaseDataSource() {

    override suspend fun postNote(note: ModelDepressionPostReq): Flow<Resource<ModelDepressionResp>> = flow{
        emit(Resource.Loading)
        emit(
            getResult {
                api.postNote(note)
            }
        )
    }
}
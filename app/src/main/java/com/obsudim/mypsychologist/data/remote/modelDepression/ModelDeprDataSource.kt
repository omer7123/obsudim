package com.obsudim.mypsychologist.data.remote.modelDepression

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.ModelDepressionPostReq
import com.obsudim.mypsychologist.data.model.ModelDepressionResp
import kotlinx.coroutines.flow.Flow

interface ModelDeprDataSource {
    suspend fun postNote(note: ModelDepressionPostReq): Flow<Resource<ModelDepressionResp>>
}
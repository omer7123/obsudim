package com.obsudim.mypsychologist.data.remote.tags

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.TagModel
import javax.inject.Inject

class TagsDataSourceImpl @Inject constructor(private val api: TagsService) :
    TagsDataSource, BaseDataSource() {

    override suspend fun getTags(): Resource<List<TagModel>> = getResult {
        api.getTags()
    }
}
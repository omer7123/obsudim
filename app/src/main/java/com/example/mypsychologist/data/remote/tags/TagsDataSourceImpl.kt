package com.example.mypsychologist.data.remote.tags

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.TagModel
import javax.inject.Inject

class TagsDataSourceImpl @Inject constructor(private val api: TagsService) :
    TagsDataSource, BaseDataSource() {

    override suspend fun getTags(): Resource<List<TagModel>> = getResult {
        api.getTags()
    }
}
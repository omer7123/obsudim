package com.example.mypsychologist.data.remote.tags

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.TagModel

interface TagsDataSource {
    suspend fun getTags(): Resource<List<TagModel>>
}
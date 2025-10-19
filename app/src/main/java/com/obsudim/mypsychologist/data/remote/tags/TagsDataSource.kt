package com.obsudim.mypsychologist.data.remote.tags

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.TagModel

interface TagsDataSource {
    suspend fun getTags(): Resource<List<TagModel>>
}
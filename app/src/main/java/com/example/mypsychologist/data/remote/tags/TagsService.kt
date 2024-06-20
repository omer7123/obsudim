package com.example.mypsychologist.data.remote.tags

import com.example.mypsychologist.data.model.TagModel
import retrofit2.Response
import retrofit2.http.GET

interface TagsService {

    @GET("/tegs/get_list_tegs")
    suspend fun getTags(): Response<List<TagModel>>
}
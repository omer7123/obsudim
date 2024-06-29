package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity

interface CbtRepository {
    suspend fun getThoughtDiaries(): HashMap<String, String>
    suspend fun getThoughtDiariesFor(clientId: String): HashMap<String, String>
    suspend fun getFreeDiariesFor(id: String): HashMap<String, String>
    suspend fun getFreeDiaries(): HashMap<String, String>


    suspend fun getThoughtDiary(id: String): Resource<ThoughtDiaryEntity>


    suspend fun saveThoughtDiary(it: ThoughtDiaryEntity): Resource<String>
    fun editAutoThought(diaryId: String, newText: String): Boolean
    fun editAlternativeThought(diaryId: String, newText: String): Boolean
    suspend fun getThoughtDiaryFor(clientId: String, id: String): ThoughtDiaryEntity
    fun saveFreeDiary(it: NewFreeDiaryEntity): Boolean


}
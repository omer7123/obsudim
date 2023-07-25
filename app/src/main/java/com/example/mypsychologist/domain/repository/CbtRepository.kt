package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.entity.DiaryRecordEntity

interface CbtRepository {
    suspend fun getThoughtDiaries(): HashMap<String, String>
    suspend fun getThoughtDiary(id: String): ThoughtDiaryEntity
    fun saveThoughtDiary(it: ThoughtDiaryEntity): Boolean
    fun editAutoThought(diaryId: String, newText: String): Boolean
    fun editAlternativeThought(diaryId: String, newText: String): Boolean
}
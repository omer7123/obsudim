package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.entity.DiaryRecordEntity

interface CbtRepository {
    fun getThoughtDiaries(): List<DiaryRecordEntity>
    fun getThoughtDiary(id: Int): ThoughtDiaryEntity
    fun saveThoughtDiary(it: ThoughtDiaryEntity): Boolean
}
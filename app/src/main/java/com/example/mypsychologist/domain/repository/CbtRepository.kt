package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.DiaryEntity
import com.example.mypsychologist.domain.entity.DiaryRecordEntity

interface CbtRepository {
    fun getThoughtDiaries(): List<DiaryRecordEntity>
    fun getThoughtDiary(id: Int): DiaryEntity
    fun saveThoughtDiary(it: DiaryEntity): Boolean
}
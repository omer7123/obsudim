package com.example.mypsychologist.data.repository

import android.util.Log
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.entity.DiaryRecordEntity
import com.example.mypsychologist.domain.repository.CbtRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class CbtRepositoryImpl @Inject constructor() : CbtRepository {

    override fun getThoughtDiaries(): List<DiaryRecordEntity> =
        listOf(
            DiaryRecordEntity(
                0,
                "Триггерная ситуация, которая произошла с клиентом и вызвала нежелательную реакцию"
            ),
            DiaryRecordEntity(
                2,
                "Триггерная ситуация, которая произошла с клиентом и вызвала нежелательную реакцию"
            ),
            DiaryRecordEntity(
                3,
                "Триггерная ситуация, которая произошла с клиентом и вызвала нежелательную реакцию"
            )
        )

    override fun getThoughtDiary(id: Int): ThoughtDiaryEntity =
        ThoughtDiaryEntity(
            "Ондатр сломал плотину",
            "гнев 80%",
            80,
            "ондатр редиска",
            "плотина сломана",
            "ондатр пытается починить плотину",
            "ондатр средней редисости",
            "гнев 30%",
            30
        )

    override fun saveThoughtDiary(it: ThoughtDiaryEntity): Boolean {
        Firebase.database("https://my-psychologist-5c7f3-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference.child(ThoughtDiaryEntity::class.simpleName!!)
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .child(it.situation)
            .setValue(it)

        return true
    }
}
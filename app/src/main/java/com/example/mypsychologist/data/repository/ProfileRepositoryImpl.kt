package com.example.mypsychologist.data.repository

import com.example.mypsychologist.di.AppModule
import com.example.mypsychologist.domain.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {

    override suspend fun deleteAccount(): Boolean =
        suspendCoroutine { continuation ->
            FirebaseAuth.getInstance().currentUser!!.delete().addOnCompleteListener{ task ->
                continuation.resume(task.isSuccessful)
            }
        }

    override fun sendFeedback(text: String): Boolean =
        try {
            val ref = Firebase.database(AppModule.URL).reference.child(FEEDBACK)
            val key = ref.push().key!!

            ref.child(key).setValue(text)
            true
        } catch (t: Throwable) {
            false
        }

    companion object {
        private const val FEEDBACK = "feedback"
    }
}
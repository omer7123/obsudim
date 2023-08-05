package com.example.mypsychologist.data.repository

import android.net.Uri
import com.example.mypsychologist.di.AppModule
import com.example.mypsychologist.domain.entity.PsychologistCard
import com.example.mypsychologist.domain.entity.PsychologistInfo
import com.example.mypsychologist.domain.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProfileRepositoryImpl @Inject constructor(private val reference: DatabaseReference) :
    ProfileRepository {

    override suspend fun deleteAccount(): Boolean =
        suspendCoroutine { continuation ->
            FirebaseAuth.getInstance().currentUser!!.delete().addOnCompleteListener { task ->
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

    override fun savePsychologist(info: PsychologistInfo): Boolean =
        try {
            val ref = Firebase.database(AppModule.URL).reference
            val key = ref.child(PsychologistInfo::class.simpleName!!).push().key!!

            ref.child(PsychologistInfo::class.simpleName!!).child(key).setValue(info)

            ref.child(PsychologistCard::class.simpleName!!).child(key)
                .setValue(PsychologistCard(info.name, info.specialization.joinToString(), 0))

            reference.child(OWN_PSYCHOLOGIST_ID).setValue(key)

            true
        } catch (t: Throwable) {
            false
        }

    override fun savePsychologist(docs: List<Uri>): Boolean =
        true

    companion object {
        private const val FEEDBACK = "feedback"
        private const val OWN_PSYCHOLOGIST_ID = "own_psychologist_id"
    }
}
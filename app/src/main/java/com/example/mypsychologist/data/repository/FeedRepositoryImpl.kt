package com.example.mypsychologist.data.repository

import com.example.mypsychologist.di.AppModule
import com.example.mypsychologist.domain.entity.FeedItemEntity
import com.example.mypsychologist.domain.entity.FeedItemUI
import com.example.mypsychologist.domain.entity.toUI
import com.example.mypsychologist.domain.repository.FeedRepository
import com.example.mypsychologist.getTypedValue
import com.example.mypsychologist.pmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FeedRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val reference: DatabaseReference
) : FeedRepository {

    override suspend fun getFeed(): List<FeedItemUI> =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            getFeedItems().pmap {
                it.value.toUI(it.key, iLiked(it.key))
            }.asReversed()
        }

    private suspend fun getFeedItems(): HashMap<String, FeedItemEntity> =
        suspendCoroutine { continuation ->
            Firebase.database(AppModule.URL).reference
                .child(FeedItemEntity::class.simpleName!!).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(
                        snapshot.getTypedValue<HashMap<String, FeedItemEntity>>() ?: hashMapOf()
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    private suspend fun iLiked(itemId: String): Boolean =
        suspendCoroutine { continuation ->
            Firebase.database(AppModule.URL).reference
                .child(FEED_ITEM_LIKERS)
                .child(itemId).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(
                        snapshot.getTypedValue<HashMap<String, String>>()
                            ?.any { it.value == auth.currentUser?.uid!! } ?: false
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun like(itemId: String): Boolean =
        try {
            val ref = Firebase.database(AppModule.URL).reference

            ref.child(FEED_ITEM_LIKERS).child(itemId)
                .child(auth.currentUser?.uid!!)
                .setValue(auth.currentUser?.uid!!)

            val newLikeScore = getLikeScore(itemId) + 1
            ref.child(FeedItemEntity::class.simpleName!!)
                .child(itemId).child(FeedItemEntity::likeScore.name)
                .setValue(newLikeScore)

            true
        } catch (t: Throwable) {
            false
        }

    override suspend fun removeLike(itemId: String): Boolean =
        try {
            val ref = Firebase.database(AppModule.URL).reference

            ref.child(FEED_ITEM_LIKERS)
                .child(itemId).child(auth.currentUser?.uid!!)
                .removeValue()

            val newLikeScore = getLikeScore(itemId) - 1
            ref.child(FeedItemEntity::class.simpleName!!)
                .child(itemId).child(FeedItemEntity::likeScore.name)
                .setValue(newLikeScore)

            true
        } catch (t: Throwable) {
            false
        }

    private suspend fun getLikeScore(itemId: String): Int =
        suspendCoroutine { continuation ->
            Firebase.database(AppModule.URL).reference
                .child(FeedItemEntity::class.simpleName!!)
                .child(itemId).child(FeedItemEntity::likeScore.name).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.value.toString().toInt())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun send(message: String): FeedItemUI =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val ref = Firebase.database(AppModule.URL).reference
                .child(FeedItemEntity::class.simpleName!!)
            val key = ref.push().key!!

            val newItem = FeedItemEntity(
                auth.currentUser?.uid!!,
                getOwnName(),
                Date().time,
                message,
                0
            )

            ref.child(key).setValue(newItem)

            FeedItemUI(key, newItem, false)
        }

    private suspend fun getOwnName(): String =
        suspendCoroutine { continuation ->
            reference.child(ProfileRepositoryImpl.NAME).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(
                        snapshot.value.toString().ifEmpty { NO_NAME }
                    )

                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    companion object {
        private const val FEED_ITEM_LIKERS = "feed item likers"
        private const val NO_NAME = "No name"
    }
}
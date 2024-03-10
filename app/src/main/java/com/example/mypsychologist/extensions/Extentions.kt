package com.example.mypsychologist.extensions

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.io.Serializable

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    SDK_INT >= 33 -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T> DataSnapshot.getTypedValue() = getValue(object :
    GenericTypeIndicator<T>() {})

suspend fun <A, B, C> Map<A, B>.pmap(transform: suspend (Map.Entry<A, B>) -> C): List<C> = coroutineScope {
    map { async { transform(it) } }.awaitAll()
}

suspend fun <A, B> List<A>.pmap(transform: suspend (A) -> B): List<B> = coroutineScope {
    map { async { transform(it) } }.awaitAll()
}



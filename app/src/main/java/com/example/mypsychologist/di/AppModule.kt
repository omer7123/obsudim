package com.example.mypsychologist.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideFirebaseUserReference(auth: FirebaseAuth): DatabaseReference =
        Firebase.database(URL)
            .reference
            .child(auth.currentUser?.uid.toString())
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    companion object {
        const val URL = "https://my-psychologist-5c7f3-default-rtdb.europe-west1.firebasedatabase.app/"
    }
}
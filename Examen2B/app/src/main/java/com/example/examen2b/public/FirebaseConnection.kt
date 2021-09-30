package com.example.examen2b.public
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseConnection {
    fun getFirestoreReference(): FirebaseFirestore {
        return Firebase.firestore
    }
}
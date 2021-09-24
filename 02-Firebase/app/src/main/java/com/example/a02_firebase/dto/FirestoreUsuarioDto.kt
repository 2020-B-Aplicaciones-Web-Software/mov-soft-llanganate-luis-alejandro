package com.example.a02_firebase.dto

data class FirestoreUsuarioDto(
    var uid: String = "",
    var email: String = "",
    var roles: ArrayList<String> = arrayListOf()
) {
}
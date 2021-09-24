package com.example.a02_firebase.dto

data class Restaurante(
    val nombre: String
) {
    override fun toString(): String {
        return "$nombre"
    }
}
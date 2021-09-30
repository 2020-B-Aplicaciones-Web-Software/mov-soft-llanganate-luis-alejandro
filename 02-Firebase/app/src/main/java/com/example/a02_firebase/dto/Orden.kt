package com.example.a02_firebase.dto

class Orden (
    val restuarante: String,
    val producto : String,
    val precio : Double,
    val cantidad : Int,
    val total: Double
    ){
    override fun toString(): String {
        return "$restuarante -$producto ($cantidad) $precio = $total"
    }
}
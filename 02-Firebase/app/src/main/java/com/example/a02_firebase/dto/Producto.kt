package com.example.a02_firebase.dto


class Producto(
    var nombre: String,
    var precio: Double,
) {
    override fun toString(): String {
        return "$nombre - $$precio"
    }
}
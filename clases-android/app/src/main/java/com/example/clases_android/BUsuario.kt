package com.example.clases_android

class BUsuario (
    val nombre: String,
    val descripcion: String
) {
    override fun toString(): String {
        return "${nombre} - ${descripcion}"
    }
}
package com.example.clases_android

class BBaseDatosMemoria {
    companion object {
        // Propiedades
        // Metodos
        // Estaticos (Singleton)
        val arregloBUsuario = arrayListOf<BUsuario>()
        init {
            arregloBUsuario
                .add(
                    BUsuario("Alejandro", "a@g.com")
                )
            arregloBUsuario
                .add(
                    BUsuario("Gabriela", "g@g.com")
                )
        }
    }
}
package com.example.clases_android

class BBaseDatosMemoria {
    companion object {
        // Propiedades
        // Metodos
        // Estaticos (Singleton)
        val arregloBUsuario = arrayListOf<Usuario>()
        init {
            arregloBUsuario
                .add(
                    Usuario("Alejandro", "a@g.com", null)
                )
            arregloBUsuario
                .add(
                    Usuario("Gabriela", "g@g.com", null)
                )
        }
    }
}
package com.example.examen2b.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examen2b.R

class CrearUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle("Crear usuario")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)
    }
}
package com.example.examen2b.activities.users

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.examen2b.R

class ListarUsuariosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_usuarios)
        setTitle("Usuarios")
        abrirActivitySinParams(R.id.btn_crear_nuevo_usuario, this, CrearUsuarioActivity::class.java)
    }

    fun abrirActivitySinParams(idButton: Int, context: Context, classRef: Class<*>) {
        findViewById<Button>(idButton).setOnClickListener {
            val intentExplicito = Intent(
                context,
                classRef
            )
            startActivity(intentExplicito)
        }
    }
}
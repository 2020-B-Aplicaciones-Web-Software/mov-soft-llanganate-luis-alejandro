package com.example.clases_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)

        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val usuario = intent.getParcelableExtra<Usuario>("Alejandro")

        Log.i("intent-explicito", "${nombre}")
        Log.i("intent-explicito", "${apellido}")
        Log.i("intent-explicito", "Usuario ${usuario}")
        val botonDevolverRespuesta = findViewById<Button>(R.id.btn_devolver_respuesta)

        botonDevolverRespuesta.setOnClickListener{
            val intentDevolverParametros = Intent()
            intentDevolverParametros.putExtra("nombreModificado", "Alejandro")
            setResult(RESULT_OK, intentDevolverParametros)
            finish()
        }

    }

}
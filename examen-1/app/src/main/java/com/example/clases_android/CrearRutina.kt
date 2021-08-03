package com.example.clases_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class CrearRutina : AppCompatActivity() {
    val dbUsuario = SqliteHelperUsuario(this)

    var CODIGO_RESPUESTA_INTENT_EXPLICITO = 402

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_rutina)
        setTitle("Crear una nueva rutina")
        val tipoEjercicio = findViewById<EditText>(R.id.c_input_tipo_ejercicio)
        val series =  findViewById<EditText>(R.id.c_input_series)
        val cantidad =  findViewById<EditText>(R.id.c_input_cantidad)
        val dia =  findViewById<EditText>(R.id.c_input_dia)
        val usuario = intent.getParcelableExtra<Usuario>("usuario")!!

        val btnRegistrarRutina = findViewById<Button>(R.id.btn_registrar_rutina)
        btnRegistrarRutina.setOnClickListener{
            val isFieldsNotBlanks = tipoEjercicio.text.isNotBlank() && series.text.isNotBlank() && cantidad.text.isNotBlank() &&
                    dia.text.isNotBlank()
            if (isFieldsNotBlanks){
                val builder= AlertDialog.Builder(this)
                builder.setTitle("Registro")
                builder.setMessage("¿Estás seguro de crear una nueva rutina?")
                builder.setPositiveButton(
                    "Si"
                ) { dialog, which ->
                    val rutinaRegistrada = dbUsuario.crearRutina(
                        usuario.idUsuario,
                        tipoEjercicio.text.toString(),
                        series.text.toString().toInt(),
                        cantidad.text.toString().toInt(),
                        dia.text.toString(),
                    )
                    if (rutinaRegistrada) {
                        Toast.makeText(
                            this,
                            "Se ha registrado esta rutina.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        abrirActividadConParametrosUsuario(ListaRutinas::class.java, usuario)
                    } else {
                        Toast.makeText(
                            this,
                            "No se ha podido registrar dicha rutina",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
                builder.setNegativeButton(
                    "No",
                    null
                )
                val dialog=builder.create()
                dialog.show()
            } else {
                Toast.makeText(
                    this,
                    "Formulario no válido",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }

    fun abrirActividadConParametrosUsuario(clase: Class<*>, usuario: Usuario){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("usuario", usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }
}

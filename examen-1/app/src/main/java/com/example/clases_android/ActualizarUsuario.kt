package com.example.clases_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ActualizarUsuario : AppCompatActivity() {

    val dbUsuario = SqliteHelperUsuario(this)
    var CODIGO_RESPUESTA_INTENT_EXPLICITO = 403

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_usuario)
        setTitle("Actualizar un nuevo usuario")
        val nombreCompleto = findViewById<EditText>(R.id.a_input_nombre_completo)
        val peso =  findViewById<EditText>(R.id.a_input_peso)
        val sexo =  findViewById<EditText>(R.id.a_input_sexo)
        val telefonoMovil =  findViewById<EditText>(R.id.a_input_telefonoCelular)
        val direccionDomicilio =  findViewById<EditText>(R.id.a_input_direccionDomicilio)

        val usuarioSeleccionado = intent.getParcelableExtra<Usuario>("usuario")
        val id:Int = usuarioSeleccionado!!.idUsuario
        nombreCompleto.setText(usuarioSeleccionado.nombreCompleto)
        peso.setText(usuarioSeleccionado.peso.toString())
        sexo.setText(usuarioSeleccionado.sexo)
        telefonoMovil.setText(usuarioSeleccionado.telefonoCelular)
        direccionDomicilio.setText(usuarioSeleccionado.direccionDomicilio)

        val btnActualizarUsuario = findViewById<Button>(R.id.btn_actualizar_usuario)
        btnActualizarUsuario.setOnClickListener{
            val isFieldsNotBlanks = nombreCompleto.text.isNotBlank() && peso.text.isNotBlank() && sexo.text.isNotBlank() &&
                    telefonoMovil.text.isNotBlank() && direccionDomicilio.text.isNotBlank()
            if (isFieldsNotBlanks){
                val builder= AlertDialog.Builder(this)
                builder.setTitle("Actualización")
                builder.setMessage("¿Estás seguro de actualizar los campos de este usuario?")
                builder.setPositiveButton(
                    "Si"
                ) { dialog, which ->
                    val usuarioRegistrado = dbUsuario.actualizarUsuario(
                        nombreCompleto.text.toString(),
                        peso.text.toString().toDouble(),
                        sexo.text.toString(),
                        telefonoMovil.text.toString(),
                        direccionDomicilio.text.toString(),
                        id
                    )
                    if (usuarioRegistrado) {
                        Toast.makeText(
                            this,
                            "Se ha actualizado correctamente el usuario ${nombreCompleto.text.toString()}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        abrirActividad(ListaUsuarios::class.java)
                    } else {
                        Toast.makeText(
                            this,
                            "No se ha podido actualizar el usuario ${nombreCompleto.text.toString()}",
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

}
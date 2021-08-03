package com.example.clases_android

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class CrearUsuario : AppCompatActivity() {

    val dbUsuario = SqliteHelperUsuario(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)
        setTitle("Crear un nuevo usuario")
        val nombreCompleto = findViewById<EditText>(R.id.c_input_nombre_completo)
        val peso =  findViewById<EditText>(R.id.c_input_peso)
        val sexo =  findViewById<EditText>(R.id.c_input_sexo)
        val telefonoMovil =  findViewById<EditText>(R.id.c_input_telefonoCelular)
        val direccionDomicilio =  findViewById<EditText>(R.id.c_input_direccionDomicilio)

        val btnRegistrarUsuario = findViewById<Button>(R.id.btn_registrar_usuario)
        btnRegistrarUsuario.setOnClickListener{
            val isFieldsNotBlanks = nombreCompleto.text.isNotBlank() && peso.text.isNotBlank() && sexo.text.isNotBlank() &&
                    telefonoMovil.text.isNotBlank() && direccionDomicilio.text.isNotBlank()
            if (isFieldsNotBlanks){
                val builder= AlertDialog.Builder(this)
                builder.setTitle("Registro")
                builder.setMessage("¿Estás seguro de crear un usuario?")
                builder.setPositiveButton(
                    "Si"
                ) { dialog, which ->
                    val usuarioRegistrado = dbUsuario.crearUsuario(
                        nombreCompleto.text.toString(),
                        peso.text.toString().toDouble(),
                        sexo.text.toString(),
                        telefonoMovil.text.toString(),
                        direccionDomicilio.text.toString()
                    )
                    if (usuarioRegistrado) {
                        Toast.makeText(
                            this,
                            "Se ha registrado el usuario ${nombreCompleto.text.toString()}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        nombreCompleto.setText("")
                        peso.setText("")
                        sexo.setText("")
                        telefonoMovil.setText("")
                        direccionDomicilio.setText("")
                    } else {
                        Toast.makeText(
                            this,
                            "No se ha podido registrar el usuario ${nombreCompleto.text.toString()}",
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

}
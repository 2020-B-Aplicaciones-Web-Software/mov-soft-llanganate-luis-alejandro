package com.example.examen2b.activities.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.examen2b.R
import com.example.examen2b.entities.Usuario
import com.example.examen2b.public.FirebaseConnection
import com.example.examen2b.public.Settings
import com.example.examen2b.public.Validator

class ActualizarUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_usuario)
        setTitle("Actualizar un nuevo usuario")
        val nombreCompleto = findViewById<EditText>(R.id.update_input_nombre_completo)
        val peso = findViewById<EditText>(R.id.update_input_peso)
        val sexo = findViewById<EditText>(R.id.update_input_sexo)
        val telefonoMovil = findViewById<EditText>(R.id.update_input_telefonoCelular)
        val direccionDomicilio = findViewById<EditText>(R.id.update_input_direccionDomicilio)

        val usuarioSeleccionado = intent.getParcelableExtra<Usuario>("usuario")
        nombreCompleto.setText(usuarioSeleccionado!!.nombreCompleto)
        peso.setText(usuarioSeleccionado.peso.toString())
        sexo.setText(usuarioSeleccionado.sexo)
        telefonoMovil.setText(usuarioSeleccionado.telefonoCelular)
        direccionDomicilio.setText(usuarioSeleccionado.direccionDomicilio)

        val btnActualizarUsuario = findViewById<Button>(R.id.btn_actualizar_usuario)
        btnActualizarUsuario.setOnClickListener{
            if (Validator.validateNotBlankInputs(arrayListOf(nombreCompleto, telefonoMovil, peso, sexo, direccionDomicilio))){
                val builder= AlertDialog.Builder(this)
                builder.setTitle("Actualización")
                builder.setMessage("¿Estás seguro de actualizar los campos de este usuario?")
                builder.setPositiveButton(
                    "Si"
                ) { dialog, which ->
                    val ref = FirebaseConnection.getFirestoreReference()
                        .collection("usuarios")
                        .whereEqualTo("nombre", usuarioSeleccionado.nombreCompleto)
                        .whereEqualTo("peso", usuarioSeleccionado.peso.toString())
                        .whereEqualTo("sexo", usuarioSeleccionado.sexo)
                        .whereEqualTo("telefono", usuarioSeleccionado.telefonoCelular)
                        .whereEqualTo("direccion", usuarioSeleccionado.direccionDomicilio)

                    ref.get()
                        .addOnSuccessListener { result ->
                            for(document in result){
                                FirebaseConnection.getFirestoreReference()
                                    .collection("usuarios")
                                    .document(document.id)
                                    .update(mapOf(
                                        "nombre" to nombreCompleto.text.toString(),
                                        "peso" to peso.text.toString(),
                                        "sexo" to sexo.text.toString(),
                                        "telefono" to telefonoMovil.text.toString(),
                                        "direccion" to direccionDomicilio.text.toString()
                                    )).addOnSuccessListener {
                                        Settings.showMessage(this, "Usuario correctamente actualizado")
                                    }.addOnFailureListener { exception ->
                                        Settings.showMessage(this, "Error en actualizar el usuario")
                                        Log.i("error-usuarios", exception.toString())
                                    }
                            }
                        }
                }
                builder.setNegativeButton(
                    "No",
                    null
                )
                val dialog=builder.create()
                dialog.show()
            } else {
                Settings.showMessage(this, "Formulario no válido")
            }
        }
    }

}
package com.example.examen2b.activities.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.examen2b.R
import com.example.examen2b.public.FirebaseConnection
import com.example.examen2b.public.Settings
import com.example.examen2b.public.Validator

class CrearUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle("Crear usuario")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)
        findViewById<Button>(R.id.btn_registrar_usuario).setOnClickListener {
            crearUsuario()
        }
    }

    fun crearUsuario(){
        // User inputs
        val nombre = findViewById<EditText>(R.id.c_input_nombre_completo)
        val peso = findViewById<EditText>(R.id.c_input_peso)
        val sexo = findViewById<EditText>(R.id.c_input_sexo)
        val telefono = findViewById<EditText>(R.id.c_input_telefonoCelular)
        val direccion = findViewById<EditText>(R.id.c_input_direccionDomicilio)

        if(
            Validator.validateNotBlankInputs(
                arrayListOf(nombre, peso, sexo, telefono, direccion)
            )
        ){
           val nuevoUsuario = hashMapOf(
               "nombre" to nombre.text.toString(),
               "peso" to peso.text.toString(),
               "sexo" to sexo.text.toString(),
               "telefono" to telefono.text.toString(),
               "direccion" to direccion.text.toString()
           )
           FirebaseConnection.getFirestoreReference()
               .collection("usuarios")
               .document("${nombre.text.toString()}|${telefono.text.toString()}")
               .set(nuevoUsuario)
               .addOnSuccessListener {
                   Settings.showMessage(this,"Se ha creado correctamente el usuario ${nombre.text.toString()}")
                   Settings.clearInputs(arrayListOf(nombre,peso,sexo,telefono,direccion))
               }
               .addOnFailureListener { exception ->
                   Settings.showMessage(this,"Error en crear el usuario")
                   Log.i("usuarios", exception.toString())
               }
        }
        else{
            Settings.showMessage(this, "Ingrese todos los datos requeridos")
        }
    }
}
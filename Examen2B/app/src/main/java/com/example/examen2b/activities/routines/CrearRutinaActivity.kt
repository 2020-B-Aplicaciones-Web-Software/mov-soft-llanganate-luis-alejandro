package com.example.examen2b.activities.routines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.examen2b.R
import com.example.examen2b.entities.Usuario
import com.example.examen2b.public.FirebaseConnection
import com.example.examen2b.public.Settings

class CrearRutinaActivity : AppCompatActivity() {

    var CODIGO_RESPUESTA_INTENT_EXPLICITO = 402

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_rutina)
        setTitle("Crear una nueva rutina")
        val btnRegistrarRutina = findViewById<Button>(R.id.btn_registrar_rutina)
        btnRegistrarRutina.setOnClickListener {
            crearRutina()
        }
    }

    fun crearRutina() {
            val tipoEjercicio = findViewById<EditText>(R.id.c_input_tipo_ejercicio)
            val series = findViewById<EditText>(R.id.c_input_series)
            val cantidad = findViewById<EditText>(R.id.c_input_cantidad)
            val dia = findViewById<EditText>(R.id.c_input_dia)
            val latitud = findViewById<EditText>(R.id.c_input_latitud)
            val longitud = findViewById<EditText>(R.id.c_input_longitud)

            val usuario = intent.getParcelableExtra<Usuario>("usuario")!!


            val isFieldsNotBlanks =
                tipoEjercicio.text.isNotBlank() && series.text.isNotBlank() && cantidad.text.isNotBlank() &&
                        dia.text.isNotBlank()
            if (isFieldsNotBlanks) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Registro")
                builder.setMessage("¿Estás seguro de crear una nueva rutina?")
                builder.setPositiveButton(
                    "Si"
                ) { dialog, which ->
                    val nuevaRutina = hashMapOf<String, Any>(
                        "tipoEjercicio" to tipoEjercicio.text.toString(),
                        "series" to series.text.toString().toInt(),
                        "cantidad" to cantidad.text.toString().toInt(),
                        "dia" to dia.text.toString(),
                        "longitud" to longitud.text.toString().toDouble(),
                        "latitud" to latitud.text.toString().toDouble()
                    )

                    val refUser = FirebaseConnection.getFirestoreReference()
                        .collection("usuarios")
                        .whereEqualTo("nombre", usuario.nombreCompleto)
                        .whereEqualTo("telefono", usuario.telefonoCelular)

                    refUser
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                FirebaseConnection.getFirestoreReference()
                                    .collection("usuarios")
                                    .document(document.id)
                                    .collection("rutinas")
                                    .add(nuevaRutina)
                                    .addOnSuccessListener {
                                        Settings.clearInputs(
                                            arrayListOf(
                                                tipoEjercicio,
                                                series,
                                                cantidad,
                                                dia,
                                                longitud,
                                                latitud
                                            )
                                        )
                                        Settings.showMessage(
                                            this,
                                            "Se ha creado una rutina para ${usuario.nombreCompleto}"
                                        )
                                    }
                            }
                        }
                }
                builder.setNegativeButton(
                    "No",
                    null
                )
                val dialog = builder.create()
                dialog.show()
            } else {
                Settings.showMessage(this, "Formulario no válido")
            }
        }
    }


package com.example.examen2b.activities.routines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.examen2b.R
import com.example.examen2b.entities.Rutina
import com.example.examen2b.entities.Usuario
import com.example.examen2b.public.FirebaseConnection

class ActualizarRutinaActivity : AppCompatActivity() {
    var CODIGO_RESPUESTA_INTENT_EXPLICITO = 402

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_rutina)
        setTitle("Actualizar una rutina")
        val tipoEjercicio = findViewById<EditText>(R.id.update_input_tipo_ejercicio)
        val series =  findViewById<EditText>(R.id.update_input_series)
        val cantidad =  findViewById<EditText>(R.id.update_input_cantidad)
        val dia =  findViewById<EditText>(R.id.update_input_dia)
        val longitud = findViewById<EditText>(R.id.update_input_longitud)
        val latitud = findViewById<EditText>(R.id.update_input_latitud)

        val rutina = intent.getParcelableExtra<Rutina>("rutina")
        val usuario = intent.getParcelableExtra<Usuario>("usuario")!!

        val btnActualizarRutina = findViewById<Button>(R.id.btn_actualizar_rutina)
        if(rutina != null){
            tipoEjercicio.setText(rutina.tipoEjercicio)
            series.setText(rutina.numeroDeSeries!!.toInt().toString())
            cantidad.setText(rutina.cantidad!!.toInt().toString())
            dia.setText(rutina.dia.toString())
            latitud.setText(rutina.latitud.toString())
            longitud.setText(rutina.longitud.toString())
        }

        btnActualizarRutina.setOnClickListener{
            val isFieldsNotBlanks = tipoEjercicio.text.isNotBlank() && series.text.isNotBlank() && cantidad.text.isNotBlank() &&
                    dia.text.isNotBlank()
            if (isFieldsNotBlanks){
                val builder= AlertDialog.Builder(this)
                builder.setTitle("Actualización")
                builder.setMessage("¿Estás seguro de actualizar esta rutina?")
                builder.setPositiveButton(
                    "Si"
                ) { dialog, which ->
                    if(rutina != null){
                        val refUsuario = FirebaseConnection.getFirestoreReference()
                            .collection("usuarios")
                            .whereEqualTo("nombre", usuario.nombreCompleto)
                            .whereEqualTo("sexo", usuario.sexo)
                            .whereEqualTo("telefono", usuario.telefonoCelular)
                            .whereEqualTo("direccion", usuario.direccionDomicilio)

                        refUsuario
                            .get()
                            .addOnSuccessListener { result ->
                                for(document in result){
                                    val refUsuarios = FirebaseConnection.getFirestoreReference()
                                        .collection("usuarios")
                                        .document(document.id)

                                    refUsuarios.collection("rutinas")
                                        .whereEqualTo("tipoEjercicio", rutina.tipoEjercicio)
                                        .whereEqualTo("series", rutina.numeroDeSeries)
                                        .whereEqualTo("cantidad", rutina.cantidad)
                                        .whereEqualTo("dia", rutina.dia)

                                        .get()
                                        .addOnSuccessListener { resultRutina ->
                                            for(documentoRutina in resultRutina){
                                                refUsuarios
                                                    .collection("rutinas")
                                                    .document(documentoRutina.id)
                                                    .update(mapOf(
                                                        "tipoEjercicio" to tipoEjercicio.text.toString(),
                                                        "series" to series.text.toString().toInt(),
                                                        "cantidad" to cantidad.text.toString().toInt(),
                                                        "dia" to dia.text.toString(),
                                                        "longitud" to longitud.text.toString().toDouble(),
                                                        "latitud" to latitud.text.toString().toDouble()
                                                    )).addOnSuccessListener {
                                                        com.example.examen2b.public.Settings.showMessage(
                                                            this, "Se ha actualizado correctamente esta rutina"
                                                        )
                                                    }
                                            }

                                        }
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
                com.example.examen2b.public.Settings.showMessage(this, "Formulario no válido")
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

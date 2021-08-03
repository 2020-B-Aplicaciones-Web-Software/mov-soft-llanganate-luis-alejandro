package com.example.clases_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ActualizarRutina : AppCompatActivity() {
    val dbUsuario = SqliteHelperUsuario(this)
    var CODIGO_RESPUESTA_INTENT_EXPLICITO = 402

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_rutina)
        setTitle("Actualizar una rutina")
        val tipoEjercicio = findViewById<EditText>(R.id.a_input_tipo_ejercicio)
        val series =  findViewById<EditText>(R.id.a_input_series)
        val cantidad =  findViewById<EditText>(R.id.a_input_cantidad)
        val dia =  findViewById<EditText>(R.id.a_input_dia)
        val rutina = intent.getParcelableExtra<Rutina>("rutina")
        val btnActualizarRutina = findViewById<Button>(R.id.btn_actualizar_rutina)
        if(rutina != null){
            val idUsuario = rutina.idUsuario
            val id:Int = rutina.idRutina
            tipoEjercicio.setText(rutina.tipoEjercicio)
            series.setText(rutina.numeroDeSeries!!.toInt().toString())
            cantidad.setText(rutina.cantidad!!.toInt().toString())
            dia.setText(rutina.dia.toString())
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
                    val rutinaRegistrada = dbUsuario.actualizarRutina(
                        rutina.idUsuario,
                        tipoEjercicio.text.toString(),
                        series.text.toString().toInt(),
                        cantidad.text.toString().toInt(),
                        dia.text.toString(),
                        rutina.idRutina
                    )
                    if (rutinaRegistrada) {
                        Toast.makeText(
                            this,
                            "Se ha actualizado esta rutina.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(
                            this,
                            "No se ha podido actulizar dicha rutina",
                            Toast.LENGTH_SHORT
                        )
                            .show()
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

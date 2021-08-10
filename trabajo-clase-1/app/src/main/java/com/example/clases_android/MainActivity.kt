package com.example.clases_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    val CODIGO_RESPUESTA_INTENT_IMPLICITO = 402

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("")
        setContentView(R.layout.activity_main)

        val botonIrListView = findViewById<Button>(
            R.id.btn_ir_clientes_view
        )

        botonIrListView
            .setOnClickListener {
                abrirCicloVida(
                    ListaUsuarios::class.java
                )
            }



    }

    fun abrirCicloVida(
        clase: Class<*>
    ){
        val intentExplicito = Intent(
            this,
            clase
        )

        // this.startActivity(Intent)
        startActivity(intentExplicito)
    }
}
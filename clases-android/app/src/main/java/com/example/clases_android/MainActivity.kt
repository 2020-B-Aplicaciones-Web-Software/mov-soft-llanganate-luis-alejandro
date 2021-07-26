package com.example.clases_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonIrCicloVida = findViewById<Button>(
            R.id.btn_ir_ciclo_vida
        )

        botonIrCicloVida.setOnClickListener {
            abrirCicloVida(
                ACicloVida::class.java
            )
        }

        val botonIrListView = findViewById<Button>(
            R.id.btn_ir_list_view
        )

        botonIrListView
            .setOnClickListener {
                abrirCicloVida(
                    BListView::class.java
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
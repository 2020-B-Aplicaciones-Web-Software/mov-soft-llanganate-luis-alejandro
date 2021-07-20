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
            abrirCicloVida()
        }
    }

    fun abrirCicloVida(){
        val intentExplicito = Intent(
            this,
            ACicloVida::class.java
        )

        // this.startActivity(Intent)
        startActivity(intentExplicito)
    }
}
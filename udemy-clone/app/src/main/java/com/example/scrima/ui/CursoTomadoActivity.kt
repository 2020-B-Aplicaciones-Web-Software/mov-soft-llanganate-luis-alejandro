package com.example.scrima.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.scrima.R
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class CursoTomadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        setContentView(R.layout.activity_curso_tomado)
        val titulo = intent.getStringExtra("titulo")
        val instructor = intent.getStringExtra("instructor")
        val imagenUrl = intent.getStringExtra("imagenUrl")

        findViewById<TextView>(R.id.tv_titulo_curso_tomado).setText(titulo)
        findViewById<TextView>(R.id.tv_autor).setText(instructor)
        val imageView = findViewById<ImageView>(R.id.imageView_curso_tomado)
        Picasso.get().load(imagenUrl).into(imageView)
    }
}
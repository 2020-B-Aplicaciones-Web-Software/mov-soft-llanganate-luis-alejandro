package com.example.clases_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GRecyclerView : AppCompatActivity() {
    var totalLikes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grecycler_view)
        val listaUsuario = arrayListOf<Usuario>()
        val rutinaUsuario = Rutina("Kanto", 5)
        listaUsuario
            .add(
                Usuario(
                    "Vicente",
                    "1718137159",
                    rutinaUsuario
                )
            )
        listaUsuario
            .add(
                Usuario(
                    "Adrian",
                    "0198137123",
                    rutinaUsuario
                )
            )
        val recyclerViewEntrenador = findViewById<RecyclerView>(
            R.id.rv_entrenadores
        )
//        this.iniciarRecyclerView()
        iniciarRecyclerView(
            listaUsuario,
            this,
            recyclerViewEntrenador
        )

    }

    fun iniciarRecyclerView(
        lista: List<Usuario>,
        actividad: GRecyclerView,
        recyclerView: RecyclerView
    ){
        val adaptador = FRecyclerViewAdaptadorNombreCedula(
            actividad,
            lista,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)
        adaptador.notifyDataSetChanged()
    }

    fun aumentarTotalLikes() {
        totalLikes = totalLikes + 1
        val textView = findViewById<TextView>(R.id.tv_total_likes)
        textView.text = totalLikes.toString()
    }
}
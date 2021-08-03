package com.example.clases_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreCedula(
    private val contexto: GRecyclerView,
    private val listaUsuario: List<Usuario>,
    private val recyclerView: RecyclerView,
): RecyclerView.Adapter<FRecyclerViewAdaptadorNombreCedula.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView
        val cedulaTextView: TextView
        val likesTextView: TextView
        val acccionButton: Button
        var numeroLikes = 0

        init {
            nombreTextView = view.findViewById(R.id.tv_nombre)
            cedulaTextView = view.findViewById(R.id.tv_cedula)
            acccionButton = view.findViewById(R.id.btn_dar_like)
            likesTextView = view.findViewById(R.id.tv_likes)
            acccionButton.setOnClickListener {
                this.anadirLike()
            }
        }

        fun anadirLike() {
            this.numeroLikes = this.numeroLikes + 1
            likesTextView.text = this.numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }
    }

    // tamano del arreglo
    override fun getItemCount(): Int {
        return listaUsuario.size
    }

    // Setaear los datos de cada iteracion del arreglo
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val usuario = listaUsuario[position]
        holder.nombreTextView.text = usuario.nombreCompleto
        holder.cedulaTextView.text = usuario.telefonoCelular
        holder.acccionButton.text = "Like ${usuario.nombreCompleto}"
        holder.likesTextView.text = "0"
    }

    // Setaear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_vista, // Definimos la vista de nuestro recycler view
                parent,
                false,
            )
        return MyViewHolder(itemView)
    }

}
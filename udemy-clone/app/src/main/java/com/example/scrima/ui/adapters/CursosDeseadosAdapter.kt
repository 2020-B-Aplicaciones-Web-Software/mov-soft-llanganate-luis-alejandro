package com.example.scrima.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrima.R
import com.example.scrima.entities.CursoUdemy
import com.example.scrima.ui.HomeActivity
import com.squareup.picasso.Picasso

class CursosDeseadosAdapter(
    private val cursosDeseados: List<CursoUdemy>
) : RecyclerView.Adapter<CursosDeseadosAdapter.MyViewHolder>()  {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tituloTextView: TextView
        val instructorTextView: TextView
        val precioTextView: TextView
        val calificacionPromedioRatingBar : RatingBar
        val cantidadCalificadoresTextView: TextView
        val portadaImageView: ImageView

        init {
            tituloTextView = view.findViewById(R.id.tv_titulo_curso_fdeseos)
            instructorTextView = view.findViewById(R.id.tv_instructor_fdeseos)
            precioTextView = view.findViewById(R.id.tv_precio_fdeseos)
            calificacionPromedioRatingBar = view.findViewById(R.id.ratingcurso_fdeseo)
            cantidadCalificadoresTextView = view.findViewById(R.id.tv_cantidad_calificadores_fdeseos)
            portadaImageView = view.findViewById(R.id.iconImageView_fdeseos)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_deseo,
                parent,
                false,
            )
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curso = cursosDeseados[position]
        holder.tituloTextView.text = curso.titulo
        holder.instructorTextView.text = curso.instructor
        if(curso.precio != null && curso.puntuacion != null) {
            holder.precioTextView.text = "$${curso.precio}"
            holder.calificacionPromedioRatingBar.rating = curso.puntuacion.toFloat()
         }
        holder.cantidadCalificadoresTextView.text = "${curso.cantidadCalificadores}"
        Picasso.get().load(curso.urlImagen).into(holder.portadaImageView)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = cursosDeseados.size
}

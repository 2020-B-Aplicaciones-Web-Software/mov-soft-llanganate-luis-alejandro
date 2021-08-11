package com.example.scrima.ui.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.scrima.entities.CursoUdemy
import com.example.scrima.ui.HomeActivity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.scrima.R
import com.example.scrima.ui.CursoTomadoActivity
import com.example.scrima.ui.MiAprendizajeFragment
import com.squareup.picasso.Picasso

class CursosTomadosAdapter(
    private val records: List<CursoUdemy>
    ) : RecyclerView.Adapter<CursosTomadosAdapter.MyViewHolder>(){

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tituloTextView: TextView
        val instructorTextView: TextView
        val barraProgreso : ProgressBar
        val progresoTextView: TextView
        val portadaImageView: ImageView
        init {
            tituloTextView = view.findViewById(R.id.tv_titulo_curso_faprendizaje)
            instructorTextView = view.findViewById(R.id.tv_instructor_faprendizaje)
            progresoTextView = view.findViewById(R.id.tv_porcentaje_faprendizaje)
            barraProgreso = view.findViewById(R.id.pb_progreso_faprendizaje)
            portadaImageView = view.findViewById(R.id.iconImageView_faprendizaje)
            view.findViewById<CardView>(R.id.card_network_record)
                .setOnLongClickListener {
                    val pop= PopupMenu(view.context,it)
                    pop.inflate(R.menu.menu_curso)
                    pop.setOnMenuItemClickListener {item->
                        when(item.itemId)
                        {
                            R.id.navegacion_ver_curso -> {
                                Log.i("test-cuac", "${item}")
                                openActivityWithParams(view.context, CursoTomadoActivity::class.java, arrayListOf(
                                    Pair("hola", "adios")
                                ))
                            }
                        }
                        true
                    }
                    pop.show()
                    true
                }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_curso_tomado,
                parent,
                false,
            )
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curso = records[position]
        holder.tituloTextView.text = curso.titulo
        holder.instructorTextView.text = curso.instructor
        if(curso.progreso != null) {
            holder.barraProgreso.setProgress(curso.progreso)
        }
        holder.progresoTextView.text = "${curso.progreso}%"
        Picasso.get().load(curso.urlImagen).into(holder.portadaImageView)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = records.size

    fun openActivityWithParams(context: Context, classReference: Class<*>, params:  ArrayList<Pair<String, String>>) {
        val intent = Intent(
            context,
            classReference
        )
        params.forEach {
                param ->
            var (key, value) = param
            intent.putExtra(key, value)
        }
        context.startActivity(intent)
    }
}

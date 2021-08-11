package com.example.scrima.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.scrima.R
import com.example.scrima.entities.CursoUdemy
import com.example.scrima.general.Settings
import com.example.scrima.ui.adapters.CursosTomadosAdapter

class MiAprendizajeFragment : Fragment(R.layout.fragment_aprendizaje) {

    var adapter : ArrayAdapter<CursoUdemy>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_aprendizaje, container, false)
        val recordsList = Settings.datosCursosTomados
        val recyclerView= view.findViewById<RecyclerView>(
            R.id.list_record_recyclerview
        )
        initRecyclerView(recordsList, recyclerView)
        return view
    }

    fun initRecyclerView(
        list: List<CursoUdemy>,
        recyclerView: RecyclerView
    ){
        val adapter = CursosTomadosAdapter(
            list
        )
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        adapter.notifyDataSetChanged()
    }
}
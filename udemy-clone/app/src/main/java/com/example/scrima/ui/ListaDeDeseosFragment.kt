package com.example.scrima.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.scrima.R
import com.example.scrima.entities.CursoUdemy
import com.example.scrima.general.Settings
import com.example.scrima.ui.adapters.CursosDeseadosAdapter
import com.example.scrima.ui.adapters.CursosTomadosAdapter

class ListaDeDeseosFragment : Fragment(R.layout.fragment_deseos) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_deseos, container, false)
        val recordsList = Settings.datosCursosTomados
        val recyclerView= view.findViewById<RecyclerView>(
            R.id.list_deseos_recyclerview
        )
        initRecyclerView(recordsList, recyclerView)
        return view
    }

    fun initRecyclerView(
        list: List<CursoUdemy>,
        recyclerView: RecyclerView
    ){
        val adapter = CursosDeseadosAdapter(
            list
        )
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        adapter.notifyDataSetChanged()
    }
}
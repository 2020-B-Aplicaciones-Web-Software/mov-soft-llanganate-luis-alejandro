package com.example.scrima.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scrima.R
import com.example.scrima.entities.CursoUdemy
import com.example.scrima.general.Settings
import com.example.scrima.ui.adapters.CursosTomadosAdapter

class MiAprendizajeFragment : Fragment(R.layout.fragment_aprendizaje) {
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
        initRecyclerView(recordsList, HomeActivity(), recyclerView)
        return view
    }

    fun initRecyclerView(
        list: List<CursoUdemy>,
        activity: HomeActivity,
        recyclerView: RecyclerView
    ){
        val adapter = CursosTomadosAdapter(
            activity,
            list,
            recyclerView
        )
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        adapter.notifyDataSetChanged()
    }
}
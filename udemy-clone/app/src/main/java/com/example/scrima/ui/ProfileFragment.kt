package com.example.scrima.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scrima.R
import com.example.scrima.entities.CursoUdemy
import com.example.scrima.entities.Usuario
import com.example.scrima.general.OptionsItem
import com.example.scrima.general.RecyclerViewItem
import com.example.scrima.general.Settings
import com.example.scrima.general.UserItem
import com.example.scrima.ui.adapters.CursosDeseadosAdapter
import com.example.scrima.ui.adapters.PerfilUsuarioAdapter

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val recordsList = Settings.datosCursosTomados
        val recyclerView= view.findViewById<RecyclerView>(
            R.id.recyclerview_profile
        )
        initRecyclerView(recyclerView)
        return view
    }

    fun initRecyclerView(
        recyclerView: RecyclerView
    ){
        val dataSet = arrayListOf<RecyclerViewItem>(
            UserItem(
                Usuario("Luis Alejandro Llanganate Valencia", "luis.llanganate@epn.edu.ec")
            ),
            OptionsItem()
        )

        val adapter = PerfilUsuarioAdapter()
        adapter.data = dataSet
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        adapter.notifyDataSetChanged()
    }

}
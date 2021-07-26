package com.example.clases_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class BListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val arregloNumeros = arrayListOf<Int>(1,2,3,4)
        val listViewEjemplo = findViewById<ListView>(R.id.txv_ejemplo)

        val adapter =  ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Layout (Visual)
            arregloNumeros,
        )

        listViewEjemplo.adapter = adapter
        val botonAnadirItem = findViewById<Button>(R.id.btn_list_view_anadir)
        botonAnadirItem.setOnClickListener {
            anadirItems(1, arregloNumeros, adapter)
        }

//        listViewEjemplo.setOnItemClickListener(){adapterView, view, position, id ->
//            Toast.makeText(
//                this,
//                adapterView.getItemAtPosition(position).toString(),
//                Toast.LENGTH_LONG
//            ).show()
//        }

        registerForContextMenu(listViewEjemplo)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
    }

    fun anadirItems(newItem: Int, array: ArrayList<Int>, adapter: ArrayAdapter<Int>){
        array.add(newItem);
        adapter.notifyDataSetChanged();
    }

}
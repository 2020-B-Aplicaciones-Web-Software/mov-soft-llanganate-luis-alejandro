package com.example.clases_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*

class BListView : AppCompatActivity() {

    var posicionItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val arreglo = BBaseDatosMemoria.arregloBUsuario
        val listViewEjemplo = findViewById<ListView>(R.id.txv_ejemplo)

        val adapter =  ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Layout (Visual)
            arreglo,
        )

        listViewEjemplo.adapter = adapter
        val botonAnadirItem = findViewById<Button>(R.id.btn_list_view_anadir)
        botonAnadirItem.setOnClickListener {
            anadirItems(BUsuario("Anonimo", "a@g.con"), arreglo, adapter)
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

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItemSeleccionado = id
        Log.i("list-view", "List view ${posicionItemSeleccionado}")
        Log.i("list-view", "List view ${BBaseDatosMemoria.arregloBUsuario[id]}")
    }

    fun anadirItems(newItem: BUsuario, array: ArrayList<BUsuario>, adapter: ArrayAdapter<*>){
        array.add(newItem);
        adapter.notifyDataSetChanged();
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            // Editar
            R.id.mi_editar -> {
                Log.i("list-view", "Editar ${
                    BBaseDatosMemoria.arregloBUsuario[
                        posicionItemSeleccionado
                ]}")
                return true
            }
            // Eliminar
            R.id.mi_eliminar -> {
                Log.i("list-view", "Eliminar ${
                    BBaseDatosMemoria.arregloBUsuario[
                        posicionItemSeleccionado
                ]}")
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
package com.example.clases_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class ListaUsuarios : AppCompatActivity() {

    var posicionItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_usuarios)

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
          //  anadirItems(Usuario("Anonimo", "a@g.con", null), arreglo, adapter)
        }

        listViewEjemplo.setOnItemClickListener(){adapterView, view, position, id ->
            Toast.makeText(
                this,
                adapterView.getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG
            ).show()

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Titulo")
           // builder.setMessage("Mensaje")

            val seleccionUsuario = booleanArrayOf(
                true,
                false,
                false
            )
            val opciones = resources.getStringArray(R.array.string_array_opciones_dialogo)

            builder.setMultiChoiceItems(
                opciones,
                seleccionUsuario,
                { dialog, which, isChecked ->
                    Log.i("list-view", "${which} - ${isChecked}")

                }
            )

            builder.setPositiveButton(
                "Si",
                { dialog, which ->
                    Log.i("list-view", "Si")
                }
            )
            builder.setNegativeButton(
                "No",
                null
            )
            val dialogo = builder.create()
            dialogo.show()
        }

      //  registerForContextMenu(listViewEjemplo)
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

    fun anadirItems(newItem: Usuario, array: ArrayList<Usuario>, adapter: ArrayAdapter<*>){
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
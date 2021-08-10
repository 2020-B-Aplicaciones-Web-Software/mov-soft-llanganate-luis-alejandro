package com.example.clases_android

import android.content.DialogInterface
import android.content.Intent
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
    var CODIGO_RESPUESTA_INTENT_EXPLICITO = 402

    // Base de datos
    val dbUsuarios = SqliteHelperUsuario(this)
    var arregloUsuarios = mutableListOf<Usuario>()
    var adapter : ArrayAdapter<Usuario>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_usuarios)
        setTitle("Usuarios")
        arregloUsuarios = dbUsuarios.consultarUsuarios()
        val listView = findViewById<ListView>(R.id.txv_ejemplo)

        adapter =  ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Layout (Visual)
            arregloUsuarios,
        )
        listView.adapter = adapter

        val botonCrearUsuario = findViewById<Button>(R.id.btn_crear_nuevo_usuario)
        botonCrearUsuario.setOnClickListener {
            abrirActividad(CrearUsuario::class.java)
        }
        registerForContextMenu(listView)
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
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var user = adapter!!.getItem(posicionItemSeleccionado)

        return when(item?.itemId){
            // Editar
            R.id.mi_actualizarUsuario -> {
                Log.i("list-view", "Editar ${
                   abrirActividadConParametros(
                       ActualizarUsuario::class.java,
                       arregloUsuarios[posicionItemSeleccionado]
                   )
                }")
                return true
            }
            // Eliminar
            R.id.mi_eliminarUsuario -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Alerta")
                    setMessage("¿Estás seguro de eliminar un usuario?")
                    setPositiveButton("Si"){ dialog: DialogInterface, wich: Int ->
                        val usuarioSeleccionado: Int? =arregloUsuarios[posicionItemSeleccionado].idUsuario
                        if (usuarioSeleccionado != null) {
                            dbUsuarios.eliminarUsuario(usuarioSeleccionado)
                        }
                        adapter?.remove(adapter!!.getItem(posicionItemSeleccionado));
                    }
                    setNegativeButton("No", null)
                }.show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }

    fun abrirActividadConParametros(clase: Class<*>, usuario: Usuario){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("usuario", usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }

}
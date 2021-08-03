package com.example.clases_android

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ListaRutinas : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var CODIGO_RESPUESTA_INTENT_EXPLICITO = 402

    // Base de datos
    val dbRutinas = SqliteHelperUsuario(this)
    var arregloRutinas = arrayListOf<Rutina>()
    var adapter : ArrayAdapter<Rutina>? = null
    var usuario : Usuario = Usuario(0,null,null,null,null,null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_rutinas)
        setTitle("Rutinas de ejercicio")
        usuario = intent.getParcelableExtra<Usuario>("usuario")!!
        if(usuario != null){
            arregloRutinas = dbRutinas.consultarRoutinasByUsuario(usuario.idUsuario)
            mostrarListView()
        }
        val botonCrearRutina = findViewById<Button>(R.id.btn_crear_nueva_rutina)
        botonCrearRutina.setOnClickListener {
            if(usuario !== null){
                abrirActividadConParametrosUsuario(CrearRutina::class.java, usuario)
            }
        }

    }
    override fun onResume() {
        super.onResume()
        if(usuario != null)
            mostrarListView()
    }

    fun mostrarListView() {
        findViewById<TextView>(R.id.txv_show_nombre).setText("Usuario: ${usuario.nombreCompleto}")
        usuario = intent.getParcelableExtra<Usuario>("usuario")!!
        if(usuario != null){
            arregloRutinas = dbRutinas.consultarRoutinasByUsuario(usuario.idUsuario)
        }
        adapter =  ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Layout (Visual)
            arregloRutinas,
        )
        val listView = findViewById<ListView>(R.id.txv_lista_rutinas)
        registerForContextMenu(listView)
        listView.adapter = adapter
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_rutina, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItemSeleccionado = id
        Log.i("list-view", "List view ${posicionItemSeleccionado}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            // Editar
            R.id.mi_actualizarRutina -> {
                Log.i("list-view", "Editar ${arregloRutinas[posicionItemSeleccionado]}")
                abrirActividadConParametrosRutina(
                    ActualizarRutina::class.java,
                    arregloRutinas[posicionItemSeleccionado]
                )
                return true
            }
            // Eliminar
            R.id.mi_eliminarRutina -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Eliminación")
                    setMessage("¿Estás seguro de eliminar una rutina?")
                    setPositiveButton("Si"){ dialog: DialogInterface, wich: Int ->
                        val rutinaSeleccionada: Int? =arregloRutinas[posicionItemSeleccionado].idRutina
                        if (rutinaSeleccionada != null) {
                            dbRutinas.eliminarRutina(rutinaSeleccionada)
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

    fun abrirActividadConParametrosRutina(clase: Class<*>, rut: Rutina){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("rutina", rut)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }

    fun abrirActividadConParametrosUsuario(clase: Class<*>, usuario: Usuario){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("usuario", usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }


}
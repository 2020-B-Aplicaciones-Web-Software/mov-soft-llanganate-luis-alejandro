package com.example.examen2b.activities.routines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.examen2b.R
import com.example.examen2b.entities.Rutina
import com.example.examen2b.entities.Usuario
import com.example.examen2b.public.FirebaseConnection

class ListarRutinasActivity : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var CODIGO_RESPUESTA_INTENT_EXPLICITO = 402

    var arregloRutinas = arrayListOf<Rutina>()
    var adapter : ArrayAdapter<Rutina>? = null
    var usuario : Usuario = Usuario(null,null,null,null,null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_rutinas)
        setTitle("Rutinas de ejercicio")
        usuario = intent.getParcelableExtra<Usuario>("usuario")!!
        val botonCrearRutina = findViewById<Button>(R.id.btn_crear_nueva_rutina)
        botonCrearRutina.setOnClickListener {
            if(usuario !== null){
                abrirActividadConParametrosUsuario(CrearRutinaActivity::class.java, usuario)
            }
        }
        mostrarRutinas()
    }
    override fun onResume() {
        super.onResume()
        mostrarRutinas()
    }

    fun mostrarRutinas() {
        arregloRutinas = arrayListOf()
        findViewById<TextView>(R.id.txv_show_nombre).setText("Usuario: ${usuario.nombreCompleto}")
        usuario = intent.getParcelableExtra<Usuario>("usuario")!!

        val refUsuario = FirebaseConnection.getFirestoreReference()
            .collection("usuarios")
            .whereEqualTo("nombre", usuario.nombreCompleto)
            .whereEqualTo("sexo", usuario.sexo)
            .whereEqualTo("telefono", usuario.telefonoCelular)
            .whereEqualTo("direccion", usuario.direccionDomicilio)

        refUsuario
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    FirebaseConnection.getFirestoreReference()
                        .collection("usuarios")
                        .document(document.id)
                        .collection("rutinas")
                        .get()
                        .addOnSuccessListener { resultRutinas ->
                            for(documentRutina in resultRutinas){
                                arregloRutinas.add(
                                    Rutina(
                                        "${document.get("tipoEjercicio")}",
                                        "${document.get("series")}".toInt(),
                                        "${document.get("cantidad")}".toInt(),
                                        "${document.get("dia")}",
                                        "${document.get("latitud")}".toDouble(),
                                        "${document.get("longitud")}".toDouble()
                                    )
                                )
                            }
                        }
                }
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
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            // Editar
            R.id.mi_actualizar_rutina -> {
                Log.i("list-view", "Editar ${arregloRutinas[posicionItemSeleccionado]}")
                abrirActividadConParametrosRutina(
                    ActualizarRutinaActivity::class.java,
                    arregloRutinas[posicionItemSeleccionado]
                )
                return true
            }
            // Eliminar
            R.id.mi_eliminar_rutina -> {
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
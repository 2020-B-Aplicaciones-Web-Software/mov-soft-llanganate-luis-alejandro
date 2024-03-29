package com.example.examen2b.activities.routines

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
import androidx.viewpager2.widget.ViewPager2
import com.example.examen2b.R
import com.example.examen2b.activities.MapaActivity
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
                            arregloRutinas = arrayListOf()
                            for(documentRutina in resultRutinas){
                                arregloRutinas.add(
                                    Rutina(
                                        "${documentRutina.get("tipoEjercicio")}",
                                        "${documentRutina.get("series")}".toInt(),
                                        "${documentRutina.get("cantidad")}".toInt(),
                                        "${documentRutina.get("dia")}",
                                        "${documentRutina.get("longitud")}".toDouble(),
                                        "${documentRutina.get("latitud")}".toDouble(),
                                    )
                                )
                                Log.i("rutinas", "${arregloRutinas}")

                            }
                            adapter =  ArrayAdapter(
                                this,
                                android.R.layout.simple_list_item_1, // Layout (Visual)
                                arregloRutinas,
                            )
                            val listView = findViewById<ListView>(R.id.lv_lista_rutinas)
                            listView.adapter = adapter
                            registerForContextMenu(listView)

                        }
                }

            }
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
        usuario = intent.getParcelableExtra<Usuario>("usuario")!!

        return when(item?.itemId){
            // Editar
            R.id.mi_actualizar_rutina -> {
                Log.i("list-view", "Editar ${arregloRutinas[posicionItemSeleccionado]}")
                abrirActividadConParametrosRutina(
                    ActualizarRutinaActivity::class.java,
                    arregloRutinas[posicionItemSeleccionado],
                    usuario
                )
                return true
            }
            // Eliminar
            R.id.mi_eliminar_rutina -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Eliminación")
                    setMessage("¿Estás seguro de eliminar una rutina?")
                    setPositiveButton("Si"){ dialog: DialogInterface, wich: Int ->
                        val rutinaSeleccionada: Rutina =arregloRutinas[posicionItemSeleccionado]
                        eliminarRutina(rutinaSeleccionada)
                        adapter?.remove(adapter!!.getItem(posicionItemSeleccionado));
                    }
                    setNegativeButton("No", null)
                }.show()
                return true
            }

            // Go to mapa
            R.id.mi_ver_mapa -> {
                val rutinaSeleccionada: Rutina =arregloRutinas[posicionItemSeleccionado]
                abrirActividadConParametrosRutina(MapaActivity::class.java, rutinaSeleccionada, usuario)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun eliminarRutina(rutina: Rutina){
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
                    val refUsuarios = FirebaseConnection.getFirestoreReference()
                        .collection("usuarios")
                        .document(document.id)

                    refUsuarios.collection("rutinas")
                        .whereEqualTo("tipoEjercicio", rutina.tipoEjercicio)
                        .whereEqualTo("series", rutina.numeroDeSeries)
                        .whereEqualTo("cantidad", rutina.cantidad)
                        .whereEqualTo("dia", rutina.dia)
                        .get()
                        .addOnSuccessListener { resultRutina ->
                            for(documentoRutina in resultRutina){
                               refUsuarios.collection("rutinas").document(documentoRutina.id).delete()
                            }
                        }
                    mostrarRutinas()
                }
            }
    }

    fun abrirActividadConParametrosRutina(clase: Class<*>, rut: Rutina, user: Usuario){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("rutina", rut)
        intentExplicito.putExtra("usuario", user)
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
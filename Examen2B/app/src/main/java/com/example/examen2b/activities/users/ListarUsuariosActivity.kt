package com.example.examen2b.activities.users

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.example.examen2b.R
import com.example.examen2b.activities.routines.ListarRutinasActivity
import com.example.examen2b.entities.Usuario
import com.example.examen2b.public.FirebaseConnection
import com.example.examen2b.public.Settings

class ListarUsuariosActivity : AppCompatActivity() {

    var usuarios = ArrayList<Usuario>()
    var posicionItemSeleccionado = 0
    var CODIGO_RESPUESTA_INTENT_EXPLICITO = 402
    var adapter : ArrayAdapter<Usuario>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_usuarios)
        setTitle("Usuarios")
        abrirActivitySinParams(R.id.btn_crear_nuevo_usuario, this, CrearUsuarioActivity::class.java)
        listarUsuarios()
    }

    override fun onResume() {
        super.onResume()
        listarUsuarios()
    }

    override fun onRestart() {
        super.onRestart()
        listarUsuarios()
    }

    fun listarUsuarios(){
        FirebaseConnection.getFirestoreReference()
            .collection("usuarios")
            .get()
            .addOnSuccessListener { result ->
                usuarios = arrayListOf()
                for(document in result){
                    usuarios.add(
                        Usuario(
                            "${document.data.get("nombre")}",
                            "${document.data.get("peso")}".toDouble(),
                            "${document.data.get("sexo")}",
                            "${document.data.get("telefono")}",
                            "${document.data.get("direccion")}"
                        )
                    )
                }
                val listView = findViewById<ListView>(R.id.txv_ejemplo)
                adapter =  ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    usuarios,
                )
                listView.adapter = adapter
                registerForContextMenu(listView)
            }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.list_users_menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var user = adapter!!.getItem(posicionItemSeleccionado)

        return when(item?.itemId){
            // Editar
            R.id.mi_actualizarUsuario -> {
                Log.i("list-view", "Editar ${
                    abrirActividadConParametros(
                        ActualizarUsuarioActivity::class.java,
                        usuarios[posicionItemSeleccionado]
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
                        val usuarioSeleccionado: Usuario? = usuarios[posicionItemSeleccionado]
                        if (usuarioSeleccionado != null) {
                            val ref = FirebaseConnection.getFirestoreReference()
                                .collection("usuarios")
                                .whereEqualTo("nombre", usuarioSeleccionado.nombreCompleto)
                                .whereEqualTo("sexo", usuarioSeleccionado.sexo)
                                .whereEqualTo("telefono", usuarioSeleccionado.telefonoCelular)
                                .whereEqualTo("direccion", usuarioSeleccionado.direccionDomicilio)
                            ref
                                .get()
                                .addOnSuccessListener { result ->
                                    for (document in result){
                                        FirebaseConnection.getFirestoreReference()
                                            .collection("usuarios").document(document.id)
                                            .delete()
                                    }
                                    onResume()
                                }                        }
                        adapter?.remove(adapter!!.getItem(posicionItemSeleccionado));
                    }
                    setNegativeButton("No", null)
                }.show()
                return true
            }
            // Ir a rutinas de un usuario
            R.id.mi_ver_rutina -> {
                if (user != null) {
                    abrirActividadConParametros(ListarRutinasActivity::class.java, user)
                }
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirActivitySinParams(idButton: Int, context: Context, classRef: Class<*>) {
        findViewById<Button>(idButton).setOnClickListener {
            val intentExplicito = Intent(
                context,
                classRef
            )
            startActivity(intentExplicito)
        }
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
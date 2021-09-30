package com.example.a02_firebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.a02_firebase.dto.FirestoreUsuarioDto
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val CODIGO_INICIO_SESION = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonLogin = findViewById<Button>(R.id.btn_login)
        botonLogin.setOnClickListener {
            llamarLoginUsuario()
        }

        val botonSalir = findViewById<Button>(R.id.btn_salir)
        botonSalir.setOnClickListener {
            solicitarSalirDelAplicativo()
        }

        val botonProducto = findViewById<Button>(R.id.btn_producto)
        botonProducto.setOnClickListener {
            irProducto()
        }

        val botonRestaraunte = findViewById<Button>(R.id.btn_restuarante)
        botonRestaraunte.setOnClickListener {
            val intent = Intent(
                this,
                DRestaraunte::class.java
            )
            startActivity(intent)
        }

        val botonOrdenes = findViewById<Button>(R.id.btn_ordenes)
        botonOrdenes.setOnClickListener{
            val intent = Intent(
                this,
                OrdenesActivity::class.java
            )
            startActivity(intent)
        }

        val botonMapa = findViewById<Button>(R.id.btn_ir_mapa)
        botonMapa.setOnClickListener {
            val intent = Intent(
                this,
                FMapsActivity::class.java
            )
            startActivity(intent)
        }

        setearBienvenida()
    }

    fun irProducto(){
        val intent = Intent(
            this,
            CProducto::class.java
        )
        startActivity(intent)
    }

    fun llamarLoginUsuario() {
        val providers = arrayListOf(
            // lista de los proveedores
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls(
                    "https://example.com/terms.html",
                    "https://example.com/privacy.html"
                )
                .build(),
            CODIGO_INICIO_SESION
        )
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CODIGO_INICIO_SESION -> {
                if (resultCode == Activity.RESULT_OK) {
                    val usuario: IdpResponse? = IdpResponse.fromResultIntent(data)
                    if (usuario != null) {
                        if (usuario.isNewUser == true) {
                            Log.i("firebase-login", "Nuevo Usuario")
                            registrarUsuarioPorPrimeraVez(usuario)
                        } else {
                            setearUsuarioFirebase()
                            Log.i("firebase-login", "Usuario Antiguo")
                        }
                    }
                } else {
                    Log.i("firebase-login", "El usuario cancelo")
                }
            }
        }
    }

    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse){
        val usuarioLogeado = FirebaseAuth
            .getInstance()
            .getCurrentUser()
        if(usuario.email != null && usuarioLogeado != null){
            // roles : ["usuario", "admin"]
            // uid
            val db = Firebase.firestore // obtenemos referencia
            val rolesUsuario = arrayListOf("usuario") // creamos el arreglo de permisos
            val nuevoUsuario = hashMapOf<String, Any>( // {roles: .... uid: .....}
                "roles" to rolesUsuario,
                "uid" to usuarioLogeado.uid,
                "email" to usuario.email.toString()
            )
            val identificadorUsuario = usuario.email
            db.collection("usuario")
           // Forma a) Firestore crea identificador
  //              .add(nuevoUsuario)
            // Forma b) Yo seteo el identificador
                .document(identificadorUsuario.toString())
                .set(nuevoUsuario)
                .addOnSuccessListener {
                    Log.i("firebase-firestore", "Se creo")
                    setearUsuarioFirebase()
                }
                .addOnFailureListener{
                    Log.i("firebase-firestore", "Falló")
                }
        }
    }

    fun setearUsuarioFirebase() {
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        if (usuarioLocal != null) {
            if (usuarioLocal.email != null) {
                // buscar en el firestore el usuario y traerlo con todos los datos
                val db = Firebase.firestore
                val referencia = db
                    .collection("usuario")
                    .document(usuarioLocal.email.toString())
                referencia
                    .get()
                    .addOnSuccessListener {
                        val usuarioCargado = it.toObject(FirestoreUsuarioDto::class.java)
                        if(usuarioCargado != null){
                            BAuthUsuario.usuario = BUsuarioFirebase(
                                usuarioCargado.uid,
                                usuarioCargado.email,
                                usuarioCargado.roles,
                            )
                            setearBienvenida()
                        }
                        Log.i("firebase-firestore", "Usuario cargado")
                        setearBienvenida()
                    }
                    .addOnFailureListener {
                        Log.i("firebase-firestore", "Fallo cargar usuario")
                    }
            }
        }

    }

    fun solicitarSalirDelAplicativo(){
        AuthUI
            .getInstance()
            .signOut(this)
            .addOnCompleteListener {
                BAuthUsuario.usuario = null
                setearBienvenida()
            }
    }

    fun setearBienvenida() {
        val textViewBienvenida = findViewById<TextView>(R.id.tv_bienvenida)
        val botonLogin = findViewById<Button>(R.id.btn_login)
        val botonSalir = findViewById<Button>(R.id.btn_salir)
        val botonProducto = findViewById<Button>(R.id.btn_producto)
        val botonRestaurante = findViewById<Button>(R.id.btn_restuarante)
        val botonOrdenes = findViewById<Button>(R.id.btn_ordenes)
        if (BAuthUsuario.usuario != null) {
            textViewBienvenida.text = "Bienvenido ${BAuthUsuario.usuario?.email}"
            botonLogin.visibility = View.INVISIBLE
            botonSalir.visibility = View.VISIBLE
            botonProducto.visibility = View.VISIBLE
            botonRestaurante.visibility = View.VISIBLE
            botonOrdenes.visibility = View.VISIBLE
        } else {
            textViewBienvenida.text = "Ingresa al aplicativo"
            botonLogin.visibility = View.VISIBLE
            botonSalir.visibility = View.INVISIBLE
            botonProducto.visibility = View.INVISIBLE
            botonRestaurante.visibility = View.INVISIBLE
            botonOrdenes.visibility = View.INVISIBLE
        }
    }


}
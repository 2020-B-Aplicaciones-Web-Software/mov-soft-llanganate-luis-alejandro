package com.example.examen2b.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examen2b.R
import com.example.examen2b.entities.Rutina
import com.example.examen2b.entities.Usuario
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.examen2b.activities.routines.ListarRutinasActivity
import com.example.examen2b.public.FirebaseConnection
import com.example.examen2b.public.Settings
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MapaActivity : AppCompatActivity() {

    private lateinit var mapa: GoogleMap

    var permisos = false

    var latitud = 0.0
    var longitud = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        solicitarPermisos()
        setTitle("Ubicación")

        val rutina = intent.getParcelableExtra<Rutina>("rutina")
        val usuario = intent.getParcelableExtra<Usuario>("usuario")!!
        abrirActivitySinParams(R.id.btn_volver,this, ListarRutinasActivity::class.java)

        val refUsuario = FirebaseConnection.getFirestoreReference()
            .collection("usuarios")
            .whereEqualTo("nombre", usuario.nombreCompleto)
            .whereEqualTo("sexo", usuario.sexo)
            .whereEqualTo("telefono", usuario.telefonoCelular)
            .whereEqualTo("direccion", usuario.direccionDomicilio)


        if(rutina != null) {
            refUsuario
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val refUsuarios = FirebaseConnection.getFirestoreReference()
                            .collection("usuarios")
                            .document(document.id)

                        refUsuarios.collection("rutinas")
                            .whereEqualTo("tipoEjercicio", rutina.tipoEjercicio)
                            .whereEqualTo("series", rutina.numeroDeSeries)
                            .whereEqualTo("cantidad", rutina.cantidad)
                            .whereEqualTo("dia", rutina.dia)

                            .get()
                            .addOnSuccessListener { resultRutinas ->
                                for (documentoRutinas in resultRutinas) {
                                    latitud = "${documentoRutinas.data.get("latitud")}".toDouble()
                                    longitud = "${documentoRutinas.data.get("longitud")}".toDouble()
                                }

                                val fragmentoMapa = supportFragmentManager
                                    .findFragmentById(R.id.map) as SupportMapFragment
                                fragmentoMapa.getMapAsync { googleMap ->
                                    if (googleMap != null) {
                                        mapa = googleMap
                                        establecerConfiguracionMapa()
                                        Log.i("maps", "${LatLng(latitud, longitud)}")
                                        val zoom = 17f
                                        anadirMarcador(LatLng(latitud, longitud), "Ubicación rutina")
                                        moverCamaraConZoom(LatLng(latitud, longitud), zoom)
                                    }
                                }
                            }
                    }
                }
        }
    }

    fun solicitarPermisos() {
        val contexto = this.applicationContext
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if (tienePermisos) {
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf( //Arreglo Permisos
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1 //Codigo de peticion de los permisos
            )
        }
    }

    fun anadirMarcador(latLng: LatLng, title: String) {
        mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
    }

    fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f) {
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )
    }

    fun establecerConfiguracionMapa() {
        val contexto = this.applicationContext
        with(mapa) {
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if (tienePermisos) {
                mapa.isMyLocationEnabled = true // no tenemos aun permisos
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true // no tenemos aun permisos
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
}
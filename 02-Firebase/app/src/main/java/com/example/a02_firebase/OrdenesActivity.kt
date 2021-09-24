package com.example.a02_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.a02_firebase.dto.Orden
import com.example.a02_firebase.dto.Producto
import com.example.a02_firebase.dto.Restaurante
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OrdenesActivity : AppCompatActivity() {

    val db = Firebase.firestore
    var restaurantes = ArrayList<Restaurante>()
    var productos = ArrayList<Producto>()
    var posicionRestaraunte : Int? = null
    var nombreRestaurante : String? = null
    var posicionProducto : Int? = null
    var nombreProducto : String =  ""
    var precioProducto : Double = 0.0
    var acumulado : Double = 0.0
    var ordenes = ArrayList<Orden>()
    var orderAdapter : ArrayAdapter<Orden>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordenes)

        findViewById<TextView>(R.id.tv_total).setText("0.0")


        obtenerRestaurantes()
        obtenerProductos()

        listarProductos()
        findViewById<Button>(R.id.btn_anadir_lista_producto)
            .setOnClickListener {
                setOrden()
            }
    }

    fun listarRestaurantes(){
        val spinner = findViewById<Spinner>(R.id.s_restaurantes)
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            restaurantes
        )

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                posicionRestaraunte = position
                nombreRestaurante = restaurantes[posicionRestaraunte!!].nombre
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun listarProductos(){
        val spinner = findViewById<Spinner>(R.id.s_producto)
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            productos
        )

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                posicionProducto = position
                nombreProducto = productos[posicionProducto!!].nombre
                precioProducto = productos[posicionProducto!!].precio
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun obtenerRestaurantes(){
        val referencia = db.collection("restaurante")
            .get()
            .addOnSuccessListener {
                it.documents.forEach{
                        iteracion ->
                    restaurantes.add(Restaurante(iteracion.get("nombre").toString()))
                }
                listarRestaurantes()
            }
    }

    fun obtenerProductos(){
        val referencia = db.collection("producto")
            .get()
            .addOnSuccessListener {
                it.documents.forEach{
                        iteracion ->
                    productos.add(Producto(iteracion.get("nombre").toString(), iteracion.get("precio").toString().toDouble()))
                }
                listarProductos()
            }
    }

    fun setOrden(){
        val cantidad = findViewById<EditText>(R.id.et_cantidad_producto)
        if(cantidad.text.isNotBlank() && cantidad.text.toString().toInt() != 0){
            val listOdenes = findViewById<ListView>(R.id.listview_lista_productos)
            val total = precioProducto!! * cantidad.text.toString().toInt()
            acumulado = acumulado + total
            Log.i("nombre-res", "$nombreRestaurante")

            ordenes.add(
                Orden(
                    nombreRestaurante!!,
                    nombreProducto!!,
                    precioProducto!!,
                    cantidad.text.toString().toInt(),
                    total
                )
            )
            orderAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ordenes)
            listOdenes.adapter = orderAdapter
            orderAdapter!!.notifyDataSetChanged()
        }else{
            Toast.makeText(this, "Llene los campos por favor", Toast.LENGTH_SHORT).show()
        }
        cantidad.setText("")
        findViewById<TextView>(R.id.tv_total).setText(acumulado.toString())
    }

    fun crearOrden(){

    }

}
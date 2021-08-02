package com.example.clases_android

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelperUsuario(
    contexto: Context?
): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario =
            """
                CREATE TABLE USUARIO(
                    id INTEGER PRIMARY KEY AUTOINTECREMENT,
                    nombre VARCHAR(50),
                    descripcion VARCHAR(50)
                )
            """.trimIndent()
        Log.i("bdd", "Creando la tabla de usuario")
            db?.execSQL(scriptCrearTablaUsuario)
    }

    fun crearUsuarioFormularop(
        nombre: String,
        descripcion: String
        ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resutladoEscritura: Long = conexionEscritura
            .insert(
                "USUARIO",
                null,
                valoresAGuardar
            )
        conexionEscritura.close()
        return if (resutladoEscritura.toInt() == -1) false else true
    }

    fun consultarUsuarioPorId(id:Int) : EUsuarioBDD {
     val scriptConsultarUsuario = "SELECT * FROM USUARIO WHERE ID + ${id}"
     val baseDatosLectura = readableDatabase
     val resultaConsultaLectura = baseDatosLectura.rawQuery(
         scriptConsultarUsuario,
         null
     )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}
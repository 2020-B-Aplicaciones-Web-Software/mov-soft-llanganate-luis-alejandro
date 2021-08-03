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
        val existeUsuario = resultaConsultaLectura.moveToFirst()
        val usuarioEncontrado = EUsuarioBDD(0, "", "")
        if(existeUsuario){
            do {
                val id = resultaConsultaLectura.getInt(0)
                val nombre = resultaConsultaLectura.getString(1)
                val descripcion = resultaConsultaLectura.getString(2)
                if (id != null) {
                    // aregloUsuarios.add(
                    // EUsuarioBDD(id, nombre, descripcion)
                    // )
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                }
            }
                while(resultaConsultaLectura.moveToNext())
        }
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun eliminarUsuarioFormulario(id: Int) : Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "USUARIO", // Tabla
                "id=?", // Clausula Where
                arrayOf(
                    id.toString()
                ) // Arreglo ordenado de parametro
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarUsuarioFormulario(
        nombre:String,
        descripcion: String,
        idActualizar: Int
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)

        val resultadoActualizacion = conexionEscritura
            .update(
                "USUARIO",
                valoresAActualizar,
                "id=?",
                arrayOf(
                    idActualizar.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}
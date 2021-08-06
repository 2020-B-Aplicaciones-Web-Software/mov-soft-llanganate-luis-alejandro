package com.example.clases_android

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SqliteHelperUsuario (
        context: Context
    ):SQLiteOpenHelper
    (
    context,
    "dbexamen",
    null,4
    ) {

    // Creacion de tablas: Usuario y RutinaEjercicio
    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCrearUsuario=
            """
            CREATE TABLE Usuario(
            idUsuario INTEGER PRIMARY KEY AUTOINCREMENT,
            nombreCompleto VARCHAR(75),
            peso DOUBLE,
            sexo VARCHAR(20),
            telefonoCelular VARCHAR(13),
            direccionDomicilio VARCHAR(90)
            );
            """.trimIndent()
        db?.execSQL(sqlCrearUsuario)
    }

    fun crearUsuario(
        nombreCompleto: String,
        peso: Double,
        sexo: String,
        telefonoCelular: String,
        direccionDomicilio: String,
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombreCompleto", nombreCompleto)
        valoresAGuardar.put("peso", peso)
        valoresAGuardar.put("sexo", sexo)
        valoresAGuardar.put("telefonoCelular", telefonoCelular)
        valoresAGuardar.put("direccionDomicilio", direccionDomicilio)
        val resutladoEscritura: Long = conexionEscritura
            .insert(
                "Usuario",
                null,
                valoresAGuardar
            )
        conexionEscritura.close()
        return if (resutladoEscritura.toInt() == -1) false else true
    }

    fun consultarUsuarios(): ArrayList<Usuario> {
        val sqlConsultarUsuarios = "SELECT * from Usuario"
        val baseDatosLectura = readableDatabase
        val resultadoConsulta = baseDatosLectura.rawQuery(sqlConsultarUsuarios, null)
        val existeUsuario = resultadoConsulta.moveToFirst()
        var usuarios = arrayListOf<Usuario>()

        if(existeUsuario){
            do{
                val id = resultadoConsulta.getInt(0)
                if(id != null){
                    val _nombreCompleto = resultadoConsulta.getString(1)
                    val _peso = resultadoConsulta.getDouble(2)
                    val _sexo = resultadoConsulta.getString(3)
                    val _telefonoCelular = resultadoConsulta.getString(4)
                    val _direccionDomicilio = resultadoConsulta.getString(5)
                    usuarios.add(
                        Usuario(id, _nombreCompleto, _peso, _sexo, _telefonoCelular, _direccionDomicilio)
                    )
                }
            }while(resultadoConsulta.moveToNext())
        }
        resultadoConsulta.close()
        baseDatosLectura.close()
        return usuarios
    }

    fun actualizarUsuario(
        nombreCompleto: String,
        peso: Double,
        sexo: String,
        telefonoCelular: String,
        direccionDomicilio: String,
        idActualizar: Int
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()

        valoresAActualizar.put("nombreCompleto", nombreCompleto)
        valoresAActualizar.put("peso", peso)
        valoresAActualizar.put("sexo", sexo)
        valoresAActualizar.put("telefonoCelular", telefonoCelular)
        valoresAActualizar.put("direccionDomicilio", direccionDomicilio)

        val resultadoActualizacion = conexionEscritura
            .update(
                "Usuario",
                valoresAActualizar,
                "idUsuario=?",
                arrayOf(
                    idActualizar.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun eliminarUsuario(id: Int) : Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "Usuario",
                "idUsuario=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        }

}
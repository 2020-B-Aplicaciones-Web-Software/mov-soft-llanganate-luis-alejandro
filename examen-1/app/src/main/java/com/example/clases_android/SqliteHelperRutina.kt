package com.example.clases_android

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelperRutina (
context: Context
):SQLiteOpenHelper
(
context,
"dbexamen",
null,1
) {

    // Creacion de la tabla RutinaEjercicio
    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCrearRutina=
            """
            CREATE TABLE RutinaEjercicio(
            idRutina INTEGER PRIMARY KEY AUTOINCREMENT,
            idUsuario INTEGER,
            tipoEjercicio VARCHAR(50),
            numeroSeries INTEGER,
            cantidad INTEGER,
            dia VARCHAR(30),
            FOREIGN KEY(idUsuario) REFERENCES Usuario(idUsuario)
            ON UPDATE CASCADE
            ON DELETE CASCADE
            );
            """.trimIndent()
        db?.execSQL(sqlCrearRutina)
    }

    fun crearRutina(
        idUsuario: Int,
        tipoEjercicio: String,
        numeroSeries: Int,
        cantidad: Int,
        dia: String,
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("idUsuario", idUsuario)
        valoresAGuardar.put("tipoEjercicio", tipoEjercicio)
        valoresAGuardar.put("numeroSeries", numeroSeries)
        valoresAGuardar.put("cantidad", cantidad)
        valoresAGuardar.put("dia", dia)
        val resutladoEscritura: Long = conexionEscritura
            .insert(
                "Rutina",
                null,
                valoresAGuardar
            )
        conexionEscritura.close()
        return if (resutladoEscritura.toInt() == -1) false else true
    }

    fun actualizarRutina(
        idUsuario: Int,
        tipoEjercicio: String,
        numeroSeries: Int,
        cantidad: Int,
        dia: String,
        idActualizar: Int
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()

        valoresAActualizar.put("idUsuario", idUsuario)
        valoresAActualizar.put("tipoEjercicio", tipoEjercicio)
        valoresAActualizar.put("numeroSeries", numeroSeries)
        valoresAActualizar.put("cantidad", cantidad)
        valoresAActualizar.put("dia", dia)

        val resultadoActualizacion = conexionEscritura
            .update(
                "Rutina",
                valoresAActualizar,
                "idRutina=?",
                arrayOf(
                    idActualizar.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun consultarRoutinasByUsuario(id:Int): ArrayList<Rutina> {
        val sqlRutinaUsuario ="SELECT * FROM Rutina WHERE idUsuario==${id}"
        val baseDatosLectura= readableDatabase
        val resultaConsultaLectura= baseDatosLectura.rawQuery(
            sqlRutinaUsuario,
            null
        )
        val existeRutina= resultaConsultaLectura.moveToFirst()
        val rutinas= arrayListOf<Rutina>()
        if(existeRutina){
            do{
                val idRutina=resultaConsultaLectura.getInt(0)
                val idUsuario=resultaConsultaLectura.getInt(1)
                val tipoEjercicio= resultaConsultaLectura.getString(2)
                val numeroSeries=resultaConsultaLectura.getInt(3)
                val cantidad=resultaConsultaLectura.getInt(4)
                val dia=resultaConsultaLectura.getString(5)
                if(idRutina!=null){
                    rutinas.add(
                    Rutina(idRutina,idUsuario, tipoEjercicio, numeroSeries, cantidad, dia)
                    )
                }
            }while (resultaConsultaLectura.moveToNext())
        }
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return rutinas
    }

    fun eliminarRutina(id: Int) : Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "Rutina",
                "idRutina=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}
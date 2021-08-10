package com.example.scrima.entities

import android.os.Parcel
import android.os.Parcelable

class CursoUdemy (
    val titulo : String?,
    val instructor: String?,
    val progreso : Int?,
    val precio: Double?,
    val urlImagen: String?,
    val puntuacion: Double?,
    val cantidadCalificadores: Int?
    ) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
    ){}

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titulo)
        parcel.writeString(instructor)
        if(progreso != null && precio != null && puntuacion != null && cantidadCalificadores != null) {
            parcel.writeInt(progreso)
            parcel.writeDouble(precio)
            parcel.writeDouble(puntuacion)
            parcel.writeInt(cantidadCalificadores)
        }
        parcel.writeString(urlImagen)
    }

    companion object CREATOR : Parcelable.Creator<CursoUdemy> {
        override fun createFromParcel(parcel: Parcel): CursoUdemy {
            return CursoUdemy(parcel)
        }
        override fun newArray(size: Int): Array<CursoUdemy?> {
            return arrayOfNulls(size)
        }
    }
}
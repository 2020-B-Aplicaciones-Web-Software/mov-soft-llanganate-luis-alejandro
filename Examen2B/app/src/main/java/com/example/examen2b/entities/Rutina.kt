package com.example.examen2b.entities

import android.os.Parcel
import android.os.Parcelable

class Rutina(
    val tipoEjercicio: String?,
    val numeroDeSeries: Int?,
    val cantidad: Int?,
    val dia: String?,
    val latitud: Double,
    val longitud: Double,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    ){
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tipoEjercicio)
        if (numeroDeSeries != null) {
            parcel.writeInt(numeroDeSeries)
        }
        if (cantidad != null) {
            parcel.writeInt(cantidad)
        }
        parcel.writeString(dia)
        parcel.writeDouble(longitud)
        parcel.writeDouble(latitud)
    }

    companion object CREATOR : Parcelable.Creator<Rutina> {
        override fun createFromParcel(parcel: Parcel): Rutina {
            return Rutina(parcel)
        }
        override fun newArray(size: Int): Array<Rutina?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Ejercicio: ${tipoEjercicio} - Series: ${numeroDeSeries} - Repeticiones: ${cantidad} - Dia: ${dia}"
    }
}
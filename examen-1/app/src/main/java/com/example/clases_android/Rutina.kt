package com.example.clases_android

import android.os.Parcel
import android.os.Parcelable

class Rutina(
    val idRutina: Int,
    val idUsuario : Int?,
    val tipoEjercicio: String?,
    val numeroDeSeries: Int?,
    val cantidad: Int?,
    val dia: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
    ){
    }

    override fun describeContents(): Int {
        return 0
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idRutina)
        if(idUsuario != null){
            parcel.writeInt(idUsuario)
        }
        parcel.writeString(tipoEjercicio)
        if (numeroDeSeries != null) {
            parcel.writeInt(numeroDeSeries)
        }
        if (cantidad != null) {
            parcel.writeInt(cantidad)
        }
        parcel.writeString(dia)
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

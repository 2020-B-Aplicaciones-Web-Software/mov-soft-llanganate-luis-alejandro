package com.example.scrima.entities

import android.os.Parcel
import android.os.Parcelable

class CursoUdemy (
    val titulo : String?,
    val instructor: String?,
    val progreso : Int?,
    val precio: Double?,
    val urlImagen: String?
    ) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString()
    ){
        }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titulo)
        parcel.writeString(instructor)
        if(progreso != null && precio != null) {
            parcel.writeInt(progreso)
            parcel.writeDouble(precio)
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
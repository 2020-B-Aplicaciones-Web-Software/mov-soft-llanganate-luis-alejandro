package com.example.clases_android

import android.os.Parcel
import android.os.Parcelable

class BUsuario (
    val nombre: String?,
    val descripcion: String?,
    val rutina: DRutina?,
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(DRutina::class.java.classLoader)
        ){
    }
    override fun describeContents(): Int {
        return 0
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeParcelable(rutina, flags)
    }
    companion object CREATOR : Parcelable.Creator<BUsuario> {
        override fun createFromParcel(parcel: Parcel): BUsuario {
            return BUsuario(parcel)
        }
        override fun newArray(size: Int): Array<BUsuario?> {
            return arrayOfNulls(size)
        }
    }
    override fun toString(): String {
        return "${nombre} - ${descripcion}"
    }
}
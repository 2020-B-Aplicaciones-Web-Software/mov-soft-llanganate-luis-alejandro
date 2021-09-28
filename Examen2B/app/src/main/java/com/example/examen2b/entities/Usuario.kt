package com.example.examen2b.entities
import android.os.Parcel
import android.os.Parcelable

class Usuario (
    val nombreCompleto: String?,
    val peso: Double?,
    val sexo: String?,
    val telefonoCelular: String?,
    val direccionDomicilio: String?,
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    ){
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombreCompleto)
        if (peso != null) {
            parcel.writeDouble(peso)
        }
        parcel.writeString(sexo)
        parcel.writeString(telefonoCelular)
        parcel.writeString(direccionDomicilio)
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }
        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "${nombreCompleto} - ${telefonoCelular}"
    }
}

package com.example.clases_android

import android.os.Parcel
import android.os.Parcelable

class DRutina(
    val tipoEjercicio : String?,
    val numeroDeSeries: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
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
    }
    companion object CREATOR : Parcelable.Creator<DRutina> {
        override fun createFromParcel(parcel: Parcel): DRutina {
            return DRutina(parcel)
        }
        override fun newArray(size: Int): Array<DRutina?> {
            return arrayOfNulls(size)
        }
    }
    override fun toString(): String {
        return "${tipoEjercicio} - ${numeroDeSeries}"
    }
}

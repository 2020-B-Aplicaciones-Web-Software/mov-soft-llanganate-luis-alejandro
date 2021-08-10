package com.example.scrima.entities

import android.os.Parcel
import android.os.Parcelable

class CursoUdemy (
    val ipGateway : String?,
    val company: String?,
    val date : String?,
    val time: String?
    ) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ){
        }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ipGateway)
        parcel.writeString(company)
        parcel.writeString(date)
        parcel.writeString(time)
    }

    override fun toString(): String {
        return "ScanRecord(gateway=$ipGateway, empresa=$company, fecha=$date, hora=$time)"
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
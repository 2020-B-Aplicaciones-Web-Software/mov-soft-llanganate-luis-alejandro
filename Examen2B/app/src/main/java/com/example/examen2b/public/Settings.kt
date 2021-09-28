package com.example.examen2b.public

import android.widget.EditText

class Settings {
    companion object {
        fun clearInputs(inputs: ArrayList<EditText>) {
            inputs.forEach {
                    input ->
                input.setText("")
            }
        }
    }
}
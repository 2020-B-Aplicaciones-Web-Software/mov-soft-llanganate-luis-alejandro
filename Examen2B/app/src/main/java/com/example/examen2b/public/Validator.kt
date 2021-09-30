package com.example.examen2b.public

import android.widget.EditText

class Validator {
    companion object {
        fun validateNotBlankInputs(inputs: ArrayList<EditText>): Boolean {
            inputs.forEach {
                    input ->
                if(input.text.isBlank()){
                    return false
                }
            }
            return true
        }
    }
}
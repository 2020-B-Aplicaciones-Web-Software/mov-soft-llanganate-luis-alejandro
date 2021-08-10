package com.example.scrima.general

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.scrima.entities.CursoUdemy

class Settings {

    companion object {
        val datosCursosTomados = arrayListOf<CursoUdemy>(
            CursoUdemy("Complete Python Developer in 2021: Zero to Mastery", "Andrei Neagoie", 50, 14.58, "https://cdn.computerhoy.com/sites/navi.axelspringer.es/public/media/image/2019/06/python.jpg", 3.5, 1500),
            CursoUdemy("Node.js, Express, MongoDB & More: The Complete Bootcamp 2021", "Jonas Schmedtmann", 20, 14.99, "https://victorroblesweb.es/wp-content/uploads/2018/01/nodejs-victorroblesweb.png", 4.8, 1200),
            CursoUdemy("Curso completo de Machine Learning: Data Science en Python", "Juan Salas", 79, 10.99, "https://img-c.udemycdn.com/course/240x135/1606018_069c.jpg", 3.5, 40000),
            CursoUdemy("Python sin fronteras: HTML, CSS, Flask y MySQL", "Nicolas Schurmann", 85, 18.58, "https://i.morioh.com/0c4cf0650b.png", 4.9, 450000),
            CursoUdemy("Universidad Angular 2021 - De Cero a Experto en Angular!\n", "Ubaldo Acosta", 29, 10.99, "https://img-c.udemycdn.com/course/240x135/2105384_9a0f_15.jpg", 4.9, 50000),
            CursoUdemy("Curso maestro de Web Scraping: Extracción de Datos de la Web", "Leonardo Kuffo", 5, 14.58, "https://img-c.udemycdn.com/course/240x135/2861742_c063.jpg", 4.7, 5899),
            CursoUdemy("Curso de Linux: todo lo necesario para ser administrador", "Alberto González", 28, 14.58, "https://img-c.udemycdn.com/course/240x135/625798_11cc_4.jpg", 3.8, 10058 ),
            CursoUdemy("Curso de Raspberry Pi 4 Model B con Python, IoT y Domotica", "Roger Mamani", 44, 14.58, "https://img-c.udemycdn.com/course/240x135/1821718_77e6_2.jpg", 3.0, 58800),
            CursoUdemy("Docker y DevOps: De novato a experto", "Daniel Echeverri Montoya", 11, 14.58, "https://img-c.udemycdn.com/course/240x135/2534680_d9c5_2.jpg", 4.5, 15823),
            CursoUdemy("The Complete Google Sheets Course: Beginner to Advanced!", "Evan Ramsey", 88, 14.58, "https://img-c.udemycdn.com/course/240x135/1916470_96db_2.jpg", 4.0, 1558),
            )
    }

}
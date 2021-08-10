package com.example.scrima.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.scrima.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    // Fragments for bottom menu navegation
    val recordsFragment  = MiAprendizajeFragment()
    val scanFragment = ListaDeDeseosFragment()
    val profileFragment = ProfileFragment()
    val idContainerView = R.id.containerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setTitle("Cursos tomados")
        // According to bottom vavegation
        setFragmentsForNavigation()
    }

    fun setFragmentsForNavigation(){
        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.navegacion_mi_aprendizaje -> {
                        setCurrentFragment(idContainerView, recordsFragment)
                        setTitle("Cursos tomados")
                        true
                    }
                    R.id.navegacion_lista_deseos -> {
                        setCurrentFragment(idContainerView, scanFragment)
                        setTitle("Lista de deseos")
                        true
                    }
                    R.id.navegacion_cuenta -> {
                        setCurrentFragment(idContainerView, profileFragment)
                        setTitle("Mi perfil")
                        true
                    }
                    else -> false
                }
            }
    }

    fun setCurrentFragment(idContainerView: Int, fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(idContainerView, fragment)
            commit()
        }
    }
}
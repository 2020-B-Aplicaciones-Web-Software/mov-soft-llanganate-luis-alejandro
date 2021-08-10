package com.example.scrima.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.scrima.R
import com.example.scrima.entities.Usuario
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    // Fragments for bottom menu navegation
    val recordsFragment  = MiAprendizajeFragment()
    val scanFragment = ListaDeDeseosFragment()
    val profileFragment = ProfileFragment()
    val idContainerView = R.id.containerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        // According to bottom vavegation
        setFragmentsForNavigation()

    }

    fun setFragmentsForNavigation(){
        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.navigation_history -> {
                        setCurrentFragment(idContainerView, recordsFragment)
                        true
                    }
                    R.id.navigation_scan -> {
                        setCurrentFragment(idContainerView, scanFragment)
                        true
                    }
                    R.id.navigation_profile -> {
                        setCurrentFragment(idContainerView, profileFragment)
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
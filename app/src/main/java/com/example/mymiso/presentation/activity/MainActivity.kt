package com.example.mymiso.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mymiso.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        if (navHostFragment != null) {
            bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

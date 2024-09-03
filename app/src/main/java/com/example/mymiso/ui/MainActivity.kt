package com.example.mymiso.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mymiso.R
import com.example.mymiso.db.UserDatabase
import com.example.mymiso.repository.NewRepository
import com.example.mymiso.ui.viewmodels.MainViewModel
import com.example.mymiso.ui.viewmodels.ViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)
        createViewModel()


    }

    private fun createViewModel() {
        val newRepository = NewRepository(UserDatabase.invoke(this))
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(newRepository)
        )[MainViewModel::class.java]
    }
}
package com.example.mymiso.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mymiso.R
import com.example.mymiso.presentation.home_screen.ui.fragments.FragmentDetailsScreen
import com.example.mymiso.presentation.navigation.MainNavigationViewModel
import com.example.mymiso.presentation.navigation.NavigationEvent
import com.example.mymiso.presentation.search_screen.fragments.FragmentSearchScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var navigationViewModel: MainNavigationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)
        createViewModel()

        lifecycleScope.launch {
            Log.d("MainActivity", "onCreate: ")
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                navigationViewModel.navigationState.collect { event ->
                    event?.let {
                        handleNavigation(event)
                        navigationViewModel.clearNavigation()
                    }

                }
            }
        }


    }

    private fun createViewModel() {
        navigationViewModel = ViewModelProvider(
            this,
        )[MainNavigationViewModel::class.java]
    }

    private fun handleNavigation(event: NavigationEvent) {
        Log.d( "MainActivity", "handleNavigation: $event")
        when (event) {
            is NavigationEvent.ToFragmentSearchScreen -> {
                replaceFragment(FragmentSearchScreen())
            }

            is NavigationEvent.ToFragmentDetailsScreen -> {
                replaceFragment(FragmentDetailsScreen())
            }

            else -> {}
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }
}
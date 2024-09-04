package com.example.mymiso.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mymiso.R
import com.example.mymiso.presentation.dineout_screen.fragments.FragmentDineoutScreen
import com.example.mymiso.presentation.landing_screen.ui.fragments.FragmentLandingScreen
import com.example.mymiso.presentation.navigation.MainNavigationViewModel
import com.example.mymiso.presentation.navigation.NavigationEvent
import com.example.mymiso.presentation.profile_screen.fragments.FragmentProfileScreen
import com.example.mymiso.presentation.search_screen.fragments.FragmentSearchScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationViewModel: MainNavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragmentLandingScreen -> {
                    replaceFragment(FragmentLandingScreen())
                    true
                }

                R.id.fragmentDineoutScreen -> {
                    replaceFragment(FragmentDineoutScreen())
                    true
                }

                R.id.fragmentProfileScreen -> {
                    replaceFragment(FragmentProfileScreen())
                    true
                }

                else -> false
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                navigationViewModel.navigationState.collect { event ->
                    event?.let {
                        handleNavigation(event)
                        navigationViewModel.clearNavigation()
                    }
                }
            }
        }

        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.fragmentLandingScreen
        }
    }

    private fun setUpViewModel() {
        navigationViewModel = ViewModelProvider(this)[MainNavigationViewModel::class.java]
    }

    private fun handleNavigation(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.ToFragmentSearchScreen -> {
                replaceFragment(FragmentSearchScreen())
            }
            // Handle other navigation events here
            else -> {}
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_fragment_container, fragment)
            .commit()
    }
}

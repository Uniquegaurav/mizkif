package com.example.mymiso.presentation.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mymiso.R
import com.example.mymiso.framework.service.LocationForegroundService
import com.example.mymiso.presentation.dineout_screen.fragments.FragmentDineoutScreen
import com.example.mymiso.presentation.landing_screen.ui.fragments.FragmentLandingScreen
import com.example.mymiso.presentation.navigation.MainNavigationViewModel
import com.example.mymiso.presentation.navigation.NavigationEvent
import com.example.mymiso.presentation.profile_screen.fragments.FragmentProfileScreen
import com.example.mymiso.presentation.search_screen.fragments.FragmentSearchScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationViewModel: MainNavigationViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            if (fineLocationGranted) {
                startLocationService()
            } else {
                // Handle permission denial
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        checkPermissionsAndStartService()

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
                        handleNavigationEvent(event)
                        navigationViewModel.clearNavigation()
                    }
                }
            }
        }

        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.fragmentLandingScreen
        }
    }

    private fun checkPermissionsAndStartService() {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            startLocationService()
        }
    }

    private fun startLocationService() {
        val intent = Intent(this, LocationForegroundService::class.java)
        ContextCompat.startForegroundService(this, intent)
    }

    private fun stopLocationService() {
        val intent = Intent(this, LocationForegroundService::class.java)
        stopService(intent)
    }


    private fun init() {
        setUpViewModel()
    }

    private fun setUpViewModel() {
        navigationViewModel = ViewModelProvider(this)[MainNavigationViewModel::class.java]
    }

    private fun handleNavigationEvent(event: NavigationEvent) {
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

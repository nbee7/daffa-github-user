package com.techtest.daffa_github_user.app

import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.techtest.daffa_github_user.R
import com.techtest.daffa_github_user.databinding.ActivityMainBinding

internal fun shouldRequestNotificationPermission(
    sdkInt: Int,
    alreadyRequested: Boolean,
    isGranted: Boolean
): Boolean = sdkInt >= Build.VERSION_CODES.TIRAMISU && !alreadyRequested && !isGranted

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.homeFragment) {
                navController.navigate(
                    R.id.homeFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.homeFragment, inclusive = false, saveState = false)
                        .setLaunchSingleTop(true)
                        .build()
                )
                true
            } else {
                NavigationUI.onNavDestinationSelected(item, navController)
            }
        }

        val preferences = getPreferences(MODE_PRIVATE)
        val alreadyRequested = preferences.getBoolean(NOTIFICATION_PERMISSION_REQUESTED, false)
        val isGranted = Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED
        if (Build.VERSION.SDK_INT >= 33 && !alreadyRequested) {
            preferences.edit().putBoolean(NOTIFICATION_PERMISSION_REQUESTED, true).apply()
            if (shouldRequestNotificationPermission(Build.VERSION.SDK_INT, alreadyRequested, isGranted)) {
                notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    private companion object {
        const val NOTIFICATION_PERMISSION_REQUESTED = "notification_permission_requested"
    }
}
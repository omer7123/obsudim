package com.example.mypsychologist

import android.graphics.Color.WHITE
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mypsychologist.databinding.ActivityMainBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), NavbarHider, ConnectionChecker {

    private lateinit var binding: ActivityMainBinding
    private var isConnection = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getAppComponent().inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigationListener()
    }

    private fun setupNavigationListener() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.navigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.navigation).setupWithNavController(navController)


        bottomNav.setOnItemReselectedListener {item->
            navController.popBackStack(item.itemId, inclusive = true)
            navController.navigate(item.itemId)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.authFragment -> {
                    transparentStatusBar()
                    bottomNav.isVisible = false
                    setLightStatusBarIcons(false)
                }
                R.id.fragment_diaries->{
                    transparentStatusBar()
                    bottomNav.isVisible = false
                    setLightStatusBarIcons(false)
                }
                R.id.registrationFragment -> {
                    transparentStatusBar()
                    bottomNav.isVisible = false
                    setLightStatusBarIcons(false)
                }

                R.id.freeDiaryTrackerMoodFragment -> {
                    transparentStatusBar()
                    bottomNav.isVisible = false
                    setLightStatusBarIcons(false)
                }

                R.id.startBoardFragment -> {
                    transparentStatusBar()
                    bottomNav.isVisible = false
                    setLightStatusBarIcons(false)
                }

                R.id.main_fragment -> {
                    transparentStatusBar()
                    bottomNav.isVisible = true
                    setLightStatusBarIcons(false)
                }

                R.id.firstBoardFragment -> {
                    transparentStatusBar()
                    bottomNav.isVisible = false
                    setLightStatusBarIcons(true)
                }

                R.id.boardFragment -> {
                    bottomNav.isVisible = false

                    setLightStatusBarIcons(true)
                }

                else -> {
                    window.statusBarColor = WHITE
                    ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
                        val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
                        view.setPadding(0, statusBarInsets.top, 0, 0)
                        insets
                    }
                    bottomNav.isVisible = true
                    setLightStatusBarIcons(true)
                }

            }
        }
    }

    private fun transparentStatusBar(){
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            view.setPadding(0, 0, 0, 0)
            insets
        }
    }
    private fun setLightStatusBarIcons(isLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val appearance = if (isLight) {
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            } else {
                0
            }
            window.insetsController?.setSystemBarsAppearance(
                appearance, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = if (isLight) {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                0
            }
        }
    }

    override fun setNavbarVisibility(it: Boolean) {
        if (::binding.isInitialized) {
            binding.navigation.isVisible = it
        }
    }

    override fun setActualItem(id: Int) {
        if (binding.navigation.selectedItemId != id) binding.navigation.selectedItemId = id
    }

    override fun isConnection() = isConnection

    companion object {
        const val TASK_CHANNEL_ID = "CHANNEL TASK"
    }
}

interface NavbarHider {
    fun setNavbarVisibility(it: Boolean)
    fun setActualItem(id: Int)
}

interface ConnectionChecker {
    fun isConnection(): Boolean
}
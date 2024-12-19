package com.example.mypsychologist

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.mypsychologist.databinding.ActivityMainBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.ui.psychologist.TasksWorker
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.kirich1409.androidnotificationdsl.channels.createNotificationChannels
import com.kirich1409.androidnotificationdsl.notification

class MainActivity : AppCompatActivity(), NavbarHider, ConnectionChecker {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private var isConnection = false

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        onSignInResult(res)
    }

    private lateinit var notification: Notification

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        getAppComponent().inject(this)

        notification = notification(
            this,
            TASK_CHANNEL_ID,
            smallIcon = R.drawable.ic_cognition
        ) {
            contentTitle = "Мяу"

        }

        startNotificationWorkManager()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        registerNetworkCallback()

//        if (auth.currentUser == null)
//            createSignInIntent()

        setupNavigationListener()
    }

    private fun startNotificationWorkManager() {
        createNotificationChannels(this) {
            channel(TASK_CHANNEL_ID, TASK_CHANNEL_NAME)
        }

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE)

        (notificationManager as NotificationManager).notify(
            TasksWorker.TASK_NOTIFICATION_ID,
            notification
        )

        val taskWorkRequest = OneTimeWorkRequest.Builder(TasksWorker::class.java).build()

        WorkManager.getInstance(this).enqueue(taskWorkRequest)
    }


    private fun registerNetworkCallback() {
        var firstFlag = true

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkRequest = NetworkRequest.Builder()
            .build()

        cm.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isConnection = true

                firstFlag = false
            }


            override fun onLost(network: Network) {
                isConnection = false
            }

            override fun onUnavailable() {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()

                firstFlag = false
            }
        })
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val message = if (result.resultCode == RESULT_OK) {
            R.string.hallow
        } else {
            R.string.network_error
        }
        Toast.makeText(this, getString(message), Toast.LENGTH_LONG).show()
    }

    private fun setupNavigationListener() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.navigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.navigation)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.main_fragment-> {
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    window.statusBarColor = android.graphics.Color.TRANSPARENT
                    bottomNav.isVisible = true

                    setLightStatusBarIcons(false)
                }
                R.id.freeDiaryFragment->{
                    bottomNav.isVisible = false
                    setLightStatusBarIcons(false)
                }
                else->{
                    bottomNav.isVisible = true
                    setLightStatusBarIcons(true)
//                window.statusBarColor = ContextCompat.getColor(this, R.color.md_theme_dark_surfaceContainerHighest)
//                WindowCompat.setDecorFitsSystemWindows(window, true)
//                val windowInsetsController =
//                    ViewCompat.getWindowInsetsController(window.decorView)
//
//                windowInsetsController?.isAppearanceLightNavigationBars = true
                }
            }
        }


        binding.navigation.setOnItemSelectedListener { item ->
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

            when (item.itemId) {
                R.id.plan_item -> {
                    navController.navigate(R.id.main_fragment)
                    true
                }
                R.id.practice_item -> {
                    navController.navigate(R.id.fragment_exercises)
                    true
                }
                R.id.theory_item -> {
                    navController.navigate(R.id.fragment_education_topics)
                    true
                }
                R.id.tests_item -> {
                    navController.navigate(R.id.fragment_tests)
                    true
                }
                else -> {
                    false
                }
            }
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
                appearance,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
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
        if (binding.navigation.selectedItemId != id)
            binding.navigation.selectedItemId = id
    }

    override fun isConnection() =
        isConnection

    companion object {
        const val TASK_CHANNEL_ID = "CHANNEL TASK"
        private const val TASK_CHANNEL_NAME = "CHANNEL TASK"
    }
}

interface NavbarHider {
    fun setNavbarVisibility(it: Boolean)
    fun setActualItem(id: Int)
}

interface ConnectionChecker {
    fun isConnection(): Boolean
}
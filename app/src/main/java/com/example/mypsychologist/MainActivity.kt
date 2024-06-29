package com.example.mypsychologist

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.mypsychologist.databinding.ActivityMainBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.ui.psychologist.TasksWorker
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.kirich1409.androidnotificationdsl.channels.createNotificationChannels

class MainActivity : AppCompatActivity(), NavbarHider, ConnectionChecker {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private var isConnection = false

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getAppComponent().inject(this)

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

        val taskWorkRequest = OneTimeWorkRequest.Builder(TasksWorker::class.java).build()

        WorkManager.getInstance(this).enqueue(taskWorkRequest)
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.Theme_MyPsychologist)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun registerNetworkCallback() {
        var firstFlag = true

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkRequest = NetworkRequest.Builder()
            .build()

        cm.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isConnection = true

            /*    if (!firstFlag)
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.connect),
                        Toast.LENGTH_LONG
                    ).show() */

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
                R.id.psychologist_item -> {
               //     navController.navigate(R.id.fragment_psychologists_with_tasks)
                    false
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun setNavbarVisibility(it: Boolean) {
        binding.navigation.isVisible = it
    }

    override fun setActualItem(id: Int) {
        if(binding.navigation.selectedItemId != id)
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
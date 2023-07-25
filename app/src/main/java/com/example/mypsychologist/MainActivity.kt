package com.example.mypsychologist

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.mypsychologist.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

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
        setContentView(R.layout.activity_main)

        getAppComponent().inject(this)

        auth = FirebaseAuth.getInstance()

        registerNetworkCallback()

        if (auth.currentUser == null)
            createSignInIntent()

        binding = ActivityMainBinding.inflate(layoutInflater)

    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
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

                if (!firstFlag)
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.connect),
                        Toast.LENGTH_LONG
                    ).show()

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

    override fun setNavbarVisibility(it: Boolean) {
        binding.navigation.isVisible = it
    }

    override fun isConnection() =
        isConnection

}

interface NavbarHider {
    fun setNavbarVisibility(it: Boolean)
}

interface ConnectionChecker {
    fun isConnection(): Boolean
}
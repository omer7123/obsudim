package com.example.mypsychologist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.mypsychologist.databinding.ActivityMainBinding
import com.example.mypsychologist.ui.main.MainFragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavbarHider {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

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
}

interface NavbarHider {
    fun setNavbarVisibility(it: Boolean)
}
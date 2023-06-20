package com.example.mypsychologist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.mypsychologist.databinding.ActivityMainBinding
import com.example.mypsychologist.ui.main.MainFragment

class MainActivity : AppCompatActivity(), NavbarHider {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setNavbarVisibility(it: Boolean) {
        binding.navigation.isVisible = it
    }
}

interface NavbarHider {
    fun setNavbarVisibility(it: Boolean)
}
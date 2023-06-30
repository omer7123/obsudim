package com.example.mypsychologist.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentMainBinding
import com.example.mypsychologist.presentation.MainViewModel
import com.example.mypsychologist.setupCard


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        setupCards()

        setupListeners()

        return binding.root
    }

    private fun setupCards() {
        binding.apply {
            setupCard(
                exercisesCard,
                R.string.exercises,
                R.string.exercises_signature,
                R.drawable.ic_neurology,
                R.drawable.primary_card
            )
            setupCard(
                educationCard,
                R.string.psychoeducation,
                R.string.psychoeducation_signature,
                R.drawable.ic_cognition,
                R.drawable.tertiary_card
            )
            setupCard(
                diagnosticsCard,
                R.string.diagnostics,
                R.string.diagnostics_signature,
                R.drawable.ic_play_shapes,
                R.drawable.primary_card
            )
        }
    }

    private fun setupListeners() {
        binding.apply {
            exercisesCard.card.setOnClickListener {
                findNavController().navigate(R.id.fragment_exercises)
            }
            diagnosticsCard.card.setOnClickListener {
                findNavController().navigate(R.id.fragment_tests)
            }
        }
    }

}
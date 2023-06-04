package com.example.mypsychologist.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentRebtBinding
import com.example.mypsychologist.setupCard

class FragmentREBT : Fragment() {
    private lateinit var binding: FragmentRebtBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRebtBinding.inflate(inflater, container, false)

        binding.problem.text = "Тест"                               //

        setupCards()

        binding.changeButton.setOnClickListener {

        }

        return binding.root
    }

    private fun setupCards() {
        binding.apply {
            setupCard(problemAnalysis, R.string.problem_analysis, R.string.problem_analysis_signature)
            setupCard(beliefsCheck, R.string.beliefs_check, R.string.beliefs_check_signature)
            setupCard(beliefsAnalysis, R.string.beliefs_analysis, R.string.beliefs_analysis_signature)
            setupCard(dialog, R.string.dialog, R.string.dialog_signature)
        }
    }
}
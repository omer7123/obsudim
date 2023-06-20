package com.example.mypsychologist.ui.exercises.cbt

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.NavbarHider
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentNewDiaryBinding

class FragmentNewDiary : Fragment() {

    private lateinit var binding: FragmentNewDiaryBinding
    private var navbarHider: NavbarHider? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavbarHider) {
            navbarHider = context
            navbarHider!!.setNavbarVisibility(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewDiaryBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.title = getString(R.string.thought_diary)
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setupHints()
        setupHelpers()

        binding.saveButton.setOnClickListener {  }

        return binding.root
    }

    private fun setupHints() {
        binding.apply {
            includeSituation.inputLayout.hint = getString(R.string.situation)
            includeMood.inputLayout.hint = getString(R.string.mood)
            includeAutoThought.inputLayout.hint = getString(R.string.auto_thought)
            includeProofs.inputLayout.hint = getString(R.string.proofs)
            includeRefutations.inputLayout.hint = getString(R.string.refutations)
            includeAlternativeThought.inputLayout.hint = getString(R.string.alternative_thought)
            includeNewMood.inputLayout.hint = getString(R.string.new_mood)
        }
    }

    private fun setupHelpers() {
        binding.apply {
            includeSituation.inputLayout.helperText = getString(R.string.situation_helper)
            includeMood.inputLayout.helperText = getString(R.string.mood_helper)
            includeAutoThought.inputLayout.helperText = getString(R.string.auto_thought_helper)
            includeProofs.inputLayout.helperText = getString(R.string.proofs_helper)
            includeRefutations.inputLayout.helperText = getString(R.string.refutations_helper)
            includeAlternativeThought.inputLayout.helperText = getString(R.string.alternative_thought_helper)
            includeNewMood.inputLayout.helperText = getString(R.string.new_mood_helper)
        }
    }

    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }
}
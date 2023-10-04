package com.example.mypsychologist.ui.psychologist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentExercisesListBinding
import com.example.mypsychologist.ui.StringAdapter
import com.example.mypsychologist.ui.autoCleared

class ExercisesFragment : Fragment() {

    private var binding: FragmentExercisesListBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesListBinding.inflate(inflater, container, false)


        setupToolbar()

        setupAdapter()

        return binding.root
    }

    private fun setupToolbar() {
        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.exercises)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setupAdapter() {
        binding.exercisesRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = StringAdapter(exercisesWithScreens().keys.toList()) {
                navigate(it)
            }
            setHasFixedSize(true)
        }
    }

    private fun navigate(title: String) {
        exercisesWithScreens()[title]?.let { screenId ->
            findNavController().navigate(screenId, bundleOf(
                CLIENT_ID to requireArguments().getString(CLIENT_ID)
            ))
        }
    }

    private fun exercisesWithScreens(): Map<String, Int> = mapOf(
        getString(R.string.thought_diary) to R.id.fragment_diaries
    )

    companion object {
        const val CLIENT_ID = "client_id"
    }
}
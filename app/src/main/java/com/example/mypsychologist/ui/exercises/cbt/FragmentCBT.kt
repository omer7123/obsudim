package com.example.mypsychologist.ui.exercises.cbt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentCbtBinding
import com.example.mypsychologist.domain.entity.DiaryCard
import com.example.mypsychologist.extensions.setupCard
import com.example.mypsychologist.ui.autoCleared

class FragmentCBT : Fragment() {

    private var binding: FragmentCbtBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCbtBinding.inflate(inflater, container, false)

        setupCards()

        return binding.root
    }

    private fun setupCards() {
        setupAdapter()

        binding.apply {
            setupCard(
                plans,
                R.string.plans,
                R.string.plans_signature,
                R.drawable.ic_task,
                R.drawable.tertiary_card
            )
            setupCard(experiments, R.string.experiments, R.string.experiments_signature)
            setupCard(ladders, R.string.ladders, R.string.ladders_signature)
        }
    }

    private fun setupAdapter() {
        val items = listOf(
            DiaryCard(getString(R.string.cbt_diary), getString(R.string.thought_diary_signature))
        )

        binding.diariesRw.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = DiariesAdapter(items) { title ->
                findNavController().navigate(
                    when (title) {
                        getString(R.string.cbt_diary) -> R.id.fragment_diaries
                        else -> R.id.fragment_diaries
                    }
                )
            }
            setHasFixedSize(true)
        }

    }
}
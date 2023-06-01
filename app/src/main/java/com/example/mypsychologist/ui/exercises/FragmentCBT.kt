package com.example.mypsychologist.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentCbtBinding
import com.example.mypsychologist.domain.entity.DiaryCard
import com.example.mypsychologist.setupCard

class FragmentCBT : Fragment() {

    private lateinit var binding: FragmentCbtBinding

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
            DiaryCard("Дневник мыслей", "Подпись тут правда на две строки, а лучше на три"),
            DiaryCard("Дневник эмоций", "Подпись тут правда на две строки, а лучше на три")
        )

        binding.diariesRw.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = DiariesAdapter(items) {

            }
            setHasFixedSize(true)
        }

    }
}
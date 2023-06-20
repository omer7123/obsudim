package com.example.mypsychologist.ui.exercises.cbt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentDiariesBinding
import com.example.mypsychologist.ui.MainAdapter

class FragmentDiaries : Fragment() {

    private lateinit var binding: FragmentDiariesBinding
    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiariesBinding.inflate(inflater, container, false)

        binding.include.toolbar.apply {
            title = getString(R.string.thought_diary)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }

        binding.newDiaryFab.setOnClickListener {
            findNavController().navigate(R.id.fragment_new_diary)
        }

        setupAdapter()

        return binding.root
    }

    private fun setupAdapter() {

        adapter = MainAdapter().apply {
            addDelegate(RecordDelegate { situation ->
                findNavController().navigate(
                    R.id.fragment_diary,
                    bundleOf(FragmentDiary.SITUATION to situation)
                )
            })

            binding.recordsRw.adapter = this
            binding.recordsRw.layoutManager = LinearLayoutManager(requireContext())
        }
                                //

        val test = listOf(
            RecordDelegateItem(
                0,
                "Триггерная ситуация, которая произошла с клиентом и вызвала нежелательную реакцию"
            ),
            RecordDelegateItem(
                0,
                "Триггерная ситуация, которая произошла с клиентом и вызвала нежелательную реакцию"
            ),
            RecordDelegateItem(
                0,
                "Триггерная ситуация, которая произошла с клиентом и вызвала нежелательную реакцию"
            )
        )
        adapter.submitList(test)
    }
}
package com.example.mypsychologist.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentExercisesBinding
import com.example.mypsychologist.ui.exercises.cbt.FragmentCBT
import com.google.android.material.tabs.TabLayoutMediator

class FragmentExercises : Fragment() {

    private lateinit var binding: FragmentExercisesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesBinding.inflate(inflater, container, false)

        binding.include.toolbar.apply {
            title = getString(R.string.exercises)

            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        setupViewPager()

        return binding.root
    }


    private fun setupViewPager() {
        val tabs = listOf(getString(R.string.cbt), getString(R.string.rebt))
        val pagerAdapter = PagerAdapter(childFragmentManager, lifecycle)

        binding.methodsViewPager.adapter = pagerAdapter

        pagerAdapter.update(listOf(FragmentCBT(), FragmentREBT()))

        TabLayoutMediator(binding.methodsTabLayout, binding.methodsViewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }
}
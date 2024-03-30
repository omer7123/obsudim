package com.example.mypsychologist.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.*
import com.example.mypsychologist.databinding.FragmentMainBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.setupCard
import com.example.mypsychologist.presentation.main.MainViewModel
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class MainFragment : Fragment() {

    private var binding: FragmentMainBinding by autoCleared()

    @Inject
    lateinit var vmFactory: MainViewModel.Factory
    private val viewModel: MainViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (activity is NavbarHider) {
            (activity as NavbarHider).setActualItem(R.id.plan_item)
        }

        binding = FragmentMainBinding.inflate(inflater, container, false)

        setupCards()

        setupListeners()

        //    checkIfPsychologist()

        return binding.root
    }

    /* private fun checkIfPsychologist() {
         val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)

         if (pref.getBoolean(getString(R.string.psychologist_is_checked), false)) {
             if (pref.getBoolean(getString(R.string.is_psychologist), false)) {
                 setupCabinetCard()
             } else {
             }
         } else {

             viewModel.checkIfPsychologist()

             viewModel.isPsychologist
                 .flowWithLifecycle(lifecycle)
                 .onEach {
                     with(pref.edit()) {
                         putBoolean(getString(R.string.psychologist_is_checked), true)
                         putBoolean(getString(R.string.is_psychologist), it)
                         apply()
                     }
                     if (it)
                         setupCabinetCard()
                 }
                 .launchIn(lifecycleScope)
        }

    }

    private fun setupCabinetCard() {
        binding.cabinetCard.card.isVisible = true
        setupCard(
            binding.cabinetCard,
            R.string.psychologist_cabinet,
            R.string.psychologist_cabinet,
            R.drawable.ic_diversity__ter,
            R.drawable.tertiary_card
        )
    } */

    private fun setupCards() {
        binding.apply {
            //    cabinetCard.card.isVisible = false
            setupCard(
                trackerCard,
                R.string.tracker,
                R.string.tracker_signature,
                backgroundRes = R.drawable.primary_card
            )
            setupCard(
                diaryCard,
                R.string.diary,
                R.string.diary_signature,
                backgroundRes = R.drawable.primary_card
            )
            setupCard(
                diagnosticsCard,
                R.string.diagnostics,
                R.string.diagnostics_signature,
                backgroundRes = R.drawable.primary_card
            )
        }
    }

    private fun setupListeners() {
        binding.apply {
            profileIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_profile)
            }
            /*         cabinetCard.card.setOnClickListener {
                         findNavController().navigate(R.id.fragment_psychologist_cabinet)
                     } */
            trackerCard.card.setOnClickListener {
                findNavController().navigate(R.id.trackerMoodFragment)
            }
            diaryCard.card.setOnClickListener {
                findNavController().navigate(R.id.freeDiaryFragment)
            }
            diagnosticsCard.card.setOnClickListener {
                findNavController().navigate(R.id.fragment_tests)
            }
        }
    }

}
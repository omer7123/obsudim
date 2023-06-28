package com.example.mypsychologist.ui.exercises.cbt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.NavbarHider
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentDiaryBinding
import com.example.mypsychologist.domain.entity.DiaryEntity
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.presentation.ThoughtDiariesScreenState
import com.example.mypsychologist.presentation.ThoughtDiaryScreenState
import com.example.mypsychologist.presentation.ThoughtDiaryViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentDiary : Fragment() {

    private lateinit var binding: FragmentDiaryBinding
    private var navbarHider: NavbarHider? = null

    @Inject
    lateinit var vmFactory: ThoughtDiaryViewModel.Factory
    private val viewModel: ThoughtDiaryViewModel by viewModels {
        ThoughtDiaryViewModel.provideFactory(
            vmFactory,
            requireArguments().getInt(ID)
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireContext().getAppComponent().exercisesComponent().create().inject(this)

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
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        setupTitles()

        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        binding.includeAutoThought.cardImage.setOnClickListener {  }

        binding.includeAlternativeThought.cardImage.setOnClickListener {  }


        return binding.root
    }

    private fun setupTitles() {
        binding.apply {
            includeToolbar.toolbar.title = getString(R.string.thought_diary)

            includeSituation.cardTitle.text = getString(R.string.situation)
            includeMood.cardTitle.text = getString(R.string.mood)
            includeAutoThought.cardTitle.text = getString(R.string.auto_thought)
            includeAutoThought.cardImage.setImageResource(R.drawable.ic_edit)
            includeProofs.cardTitle.text = getString(R.string.proofs)
            includeRefutations.cardTitle.text = getString(R.string.refutations)
            includeAlternativeThought.cardTitle.text = getString(R.string.alternative_thought)
            includeAlternativeThought.cardImage.setImageResource(R.drawable.ic_edit)
            includeNewMood.cardTitle.text = getString(R.string.new_mood)
        }
    }

    private fun render(it: ThoughtDiaryScreenState) {
        when (it) {
            is ThoughtDiaryScreenState.Data -> {
                setupRecords(it.diary)
            }
            is ThoughtDiaryScreenState.Init -> {}
            is ThoughtDiaryScreenState.Loading -> {}
            is ThoughtDiaryScreenState.Error -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupRecords(diary: DiaryEntity) {
        binding.apply {
            includeSituation.cardDescription.text = diary.situation
            includeMood.cardDescription.text = diary.mood
            includeAutoThought.cardDescription.text = diary.autoThought
            includeProofs.cardDescription.text = diary.proofs
            includeRefutations.cardDescription.text = diary.refutations
            includeAlternativeThought.cardDescription.text = diary.alternativeThought
            includeNewMood.cardDescription.text = diary.newMood
        }
    }

    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }

    companion object {
        const val ID = "situation"
    }
}
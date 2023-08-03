package com.example.mypsychologist.ui.exercises.cbt

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.*
import com.example.mypsychologist.databinding.FragmentDiaryBinding
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.presentation.ThoughtDiaryScreenState
import com.example.mypsychologist.presentation.ThoughtDiaryViewModel
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.FragmentEditField
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentThoughtDiary : Fragment() {

    private var binding: FragmentDiaryBinding by autoCleared()
    private var navbarHider: NavbarHider? = null

    @Inject
    lateinit var vmFactory: ThoughtDiaryViewModel.Factory
    private val viewModel: ThoughtDiaryViewModel by viewModels {
        ThoughtDiaryViewModel.provideFactory(
            vmFactory,
            requireArguments().getString(ID, "")
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

        setupListeners()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

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

    private fun setupListeners() {
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.includeAutoThought.cardImage.setOnClickListener {

            childFragmentManager.setFragmentResultListener(
                EDIT_AUTO_THOUGHT, viewLifecycleOwner
            ) { _, bundle ->

                bundle.getString(FragmentEditField.NEW_TEXT)?.let { newText ->
                    viewModel.editAutoThought(newText)
                    binding.includeAutoThought.cardDescription.text = newText
                }
            }

            FragmentEditField.newInstance(
                R.string.auto_thought,
                binding.includeAutoThought.cardDescription.text.toString()
            ).show(childFragmentManager, EDIT_AUTO_THOUGHT)
        }

        binding.includeAlternativeThought.cardImage.setOnClickListener {
            childFragmentManager.setFragmentResultListener(
                EDIT_ALTERNATIVE_THOUGHT, viewLifecycleOwner
            ) { _, bundle ->

                bundle.getString(FragmentEditField.NEW_TEXT)?.let { newText ->
                    viewModel.editAlternativeThought(newText)
                    binding.includeAlternativeThought.cardDescription.text = newText
                }
            }

            FragmentEditField.newInstance(
                R.string.alternative_thought,
                binding.includeAlternativeThought.cardDescription.text.toString()
            ).show(childFragmentManager, EDIT_ALTERNATIVE_THOUGHT)
        }
    }

    private fun render(it: ThoughtDiaryScreenState) {
        when (it) {
            is ThoughtDiaryScreenState.Data -> {
                setupRecords(it.diary)
            }
            is ThoughtDiaryScreenState.Init -> {}
            is ThoughtDiaryScreenState.Loading -> {
                if (!isNetworkConnect()) {
                    showToast(getString(R.string.network_error))
                }
            }
            is ThoughtDiaryScreenState.Error -> {
                showToast(getString(R.string.db_error))
            }
            is ThoughtDiaryScreenState.EditingSuccess -> {
                if (isNetworkConnect())
                    showToast(getString(R.string.success))
                else
                    showToast(getString(R.string.network_error))
            }
        }
    }

    private fun setupRecords(diary: ThoughtDiaryEntity) {
        binding.apply {
            includeSituation.cardDescription.text = diary.situation
            includeMood.cardDescription.text =
                getString(R.string.mood_with_level, diary.mood, diary.level.toString())
            includeAutoThought.cardDescription.text = diary.autoThought
            includeProofs.cardDescription.text = diary.proofs
            includeRefutations.cardDescription.text = diary.refutations
            includeAlternativeThought.cardDescription.text = diary.alternativeThought
            includeNewMood.cardDescription.text =
                getString(R.string.mood_with_level, diary.newMood, diary.newLevel.toString())
        }
    }

    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }

    companion object {
        const val ID = "situation"
        private const val EDIT_AUTO_THOUGHT = "auto_thought"
        private const val EDIT_ALTERNATIVE_THOUGHT = "alternative_thought"
    }
}
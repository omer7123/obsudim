package com.example.mypsychologist.ui.exercises.cbt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.*
import com.example.mypsychologist.databinding.FragmentNewDiaryBinding
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.presentation.NewThoughtDiaryScreenState
import com.example.mypsychologist.presentation.NewThoughtDiaryViewModel
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentNewDiary : Fragment() {

    private var binding: FragmentNewDiaryBinding by autoCleared()
    private var navbarHider: NavbarHider? = null

    @Inject
    lateinit var vmFactory: NewThoughtDiaryViewModel.Factory
    private val viewModel: NewThoughtDiaryViewModel by viewModels { vmFactory }

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
        binding = FragmentNewDiaryBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.title = getString(R.string.thought_diary)

        setupHints()
        setupHelpers()
        setupListeners()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

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
            includeAlternativeThought.inputLayout.helperText =
                getString(R.string.alternative_thought_helper)
            includeNewMood.inputLayout.helperText = getString(R.string.new_mood_helper)
        }
    }

    private fun setupListeners() {
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setupTextListeners()

        binding.saveButton.setOnClickListener { viewModel.tryToSaveDiary() }
    }

    private fun setupTextListeners() {
        binding.apply {
            includeSituation.field.addTextChangedListener {
                viewModel.setSituation(it.toString())
                includeSituation.inputLayout.error = null
            }
            includeMood.field.addTextChangedListener {
                viewModel.setMood(it.toString())
                includeMood.inputLayout.error = null
            }

            includeLevel.seekBar.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    viewModel.setLevel(p1)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
            includeAutoThought.field.addTextChangedListener {
                viewModel.setAutoThought(it.toString())
                includeAutoThought.inputLayout.error = null
            }
            includeProofs.field.addTextChangedListener {
                viewModel.setProofs(it.toString())
                includeProofs.inputLayout.error = null
            }
            includeRefutations.field.addTextChangedListener {
                viewModel.setRefutations(it.toString())
                includeRefutations.inputLayout.error = null
            }
            includeAlternativeThought.field.addTextChangedListener {
                viewModel.setAlternativeThought(it.toString())
                includeAlternativeThought.inputLayout.error = null
            }
            includeNewMood.field.addTextChangedListener {
                viewModel.setNewMood(it.toString())
                includeNewMood.inputLayout.error = null
            }

            includeNewLevel.seekBar.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    viewModel.setNewLevel(p1)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
        }
    }

    private fun render(it: NewThoughtDiaryScreenState) {
        when (it) {
            is NewThoughtDiaryScreenState.RequestResult -> {
                renderRequest(it.success)
            }
            is NewThoughtDiaryScreenState.ValidationError -> {
                renderValidationError(it.fields)
            }
            is NewThoughtDiaryScreenState.Init -> {}
        }
    }

    private fun renderRequest(isSuccess: Boolean) {

                when {
                    !isSuccess -> {
                        showToast(getString(R.string.db_error))
                    }
                    !isNetworkConnect() -> {
                        Snackbar.make(
                            binding.coordinator,
                            R.string.save_after_connect,
                            Snackbar.LENGTH_LONG
                        ).setAction(R.string.go) {
                            findNavController().popBackStack()
                        }.show()
                    }
                    else -> {
                        findNavController().popBackStack()
                        showToast(getString(R.string.success))
                    }
                }
    }

    private fun renderValidationError(fieldsWithError: List<String>) {
        val diaryMembersAndFields = mapOf(
            ThoughtDiaryEntity::situation.name to binding.includeSituation.inputLayout,
            ThoughtDiaryEntity::mood.name to binding.includeMood.inputLayout,
            ThoughtDiaryEntity::autoThought.name to binding.includeAutoThought.inputLayout,
            ThoughtDiaryEntity::proofs.name to binding.includeProofs.inputLayout,
            ThoughtDiaryEntity::refutations.name to binding.includeRefutations.inputLayout,
            ThoughtDiaryEntity::alternativeThought.name to binding.includeAlternativeThought.inputLayout,
            ThoughtDiaryEntity::newMood.name to binding.includeNewMood.inputLayout
        )

        fieldsWithError.forEach {
            diaryMembersAndFields[it]?.error = getString(R.string.necessary_to_fill)
        }
    }

    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }
}
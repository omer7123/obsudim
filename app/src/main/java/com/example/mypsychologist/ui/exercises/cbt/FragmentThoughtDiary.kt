package com.example.mypsychologist.ui.exercises.cbt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.NavbarHider
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentThoughtDiaryBinding
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailResultEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.core.BaseStateUI
import com.example.mypsychologist.presentation.exercises.ThoughtDiaryViewModel
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentThoughtDiary : Fragment() {

    private var binding: FragmentThoughtDiaryBinding by autoCleared()
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
        binding = FragmentThoughtDiaryBinding.inflate(inflater, container, false)

        setupTitles()

        setupListeners()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        viewModel.loadDiary(requireArguments().getString(RESULT_ID).toString())
        return binding.root
    }

    private fun setupTitles() {
        binding.apply {
            includeToolbar.toolbar.title = getString(R.string.cbt_diary)

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

    }

    private fun render(it: BaseStateUI<ExerciseDetailResultEntity>) {
        when (it) {
            is BaseStateUI.Content -> {
                setupRecords(it.data.result)
            }

            is BaseStateUI.Error -> {
                requireContext().showToast(it.msg)
            }

            is BaseStateUI.Loading -> {

            }
            is BaseStateUI.Initial -> Unit
            /*    is ThoughtDiaryScreenState.EditingSuccess -> {
                    if (isNetworkConnect())
                        requireContext().showToast(getString(R.string.success))
                    else
                        requireContext().showToast(getString(R.string.network_error))
                } */
        }
    }

    private fun setupRecords(diary: List<ExerciseResultEntity>) {
        binding.apply {
            includeSituation.cardDescription.text = diary[0].value
            includeMood.cardDescription.text = diary[3].value

            includeAutoThought.cardDescription.text = diary[4].value
            includeProofs.cardDescription.text = diary[5].value
            includeRefutations.cardDescription.text = diary[6].value
            includeAlternativeThought.cardDescription.text = diary[7].value
            includeNewMood.cardDescription.text =
                getString(R.string.mood_with_level, diary[8].value, diary[9].value)
        }
    }

    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }

    companion object {
        const val ID = "id"
        const val RESULT_ID = "RESULT_ID"
    }
}
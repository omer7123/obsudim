package com.example.mypsychologist.ui.exercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentExercisesBinding
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.setupCardPractice
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.core.BaseStateUI
import com.example.mypsychologist.presentation.exercises.exercisesFragment.REBTViewModel
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.cbt.NewFreeDiaryFragment
import com.example.mypsychologist.ui.exercises.cbt.TrackerMoodFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class
FragmentExercises : Fragment() {

    private var binding: FragmentExercisesBinding by autoCleared()

    private val exercisesList = arrayListOf<ExerciseEntity>()

    @Inject
    lateinit var vmFactory: REBTViewModel.Factory
    private val viewModel: REBTViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesBinding.inflate(inflater, container, false)

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                render(state)
            }
            .launchIn(lifecycleScope)

        setupCards()
        initListener()

        return binding.root
    }

    private fun render(state: BaseStateUI<List<ExerciseEntity>>) {
        when (state) {
            is BaseStateUI.Content -> {
                exercisesList.addAll(state.data)
                binding.networkPlaceholder.layout.isVisible = false
                binding.progressCircular.isVisible = false
            }

            is BaseStateUI.Loading -> {
                if (!isNetworkConnect()) {
                    binding.networkPlaceholder.layout.isVisible = true
                } else {
                    binding.progressCircular.isVisible = true
                }
            }

            is BaseStateUI.Error -> {
                binding.networkPlaceholder.layout.isVisible = false
                binding.progressCircular.isVisible = false

                requireContext().showToast(state.msg)
            }

            is BaseStateUI.Initial -> Unit
        }
    }

    private fun initListener() {
        with(binding) {
            diaryCard.root.setOnClickListener {
                findNavController().navigate(
                    R.id.fragment_diaries,
                    bundleOf(EXERCISE_ID to exercisesList.find { it.title == "КПТ-дневник" }!!.id)
                )
            }

            trackerCard.root.setOnClickListener {
                TrackerMoodFragment().show(childFragmentManager, TrackerMoodFragment.SHOW)
            }

            diaryFreeBtn.setOnClickListener {
                findNavController().navigate(
                    R.id.freeDiaryFragment,
                    bundleOf(
                        NewFreeDiaryFragment.KPT_ID to exercisesList.find { it.title == "КПТ-дневник" }!!.id
                    )
                )
            }
        }

        binding.include.apply {
            toolbar.title = getString(R.string.practice)

            profileIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_profile)
            }
            psychologistsIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_psychologists_with_tasks)
            }
        }
    }

    private fun setupCards() {
        binding.apply {
            setupCardPractice(
                trackerCard,
                R.string.tracker_mood_title,
                R.drawable.ic_tracker_mood_practice
            )
            setupCardPractice(
                diaryCard,
                R.string.cbt_diary,
                R.drawable.ic_diary_practice
            )
        }
    }

    companion object {
        private const val EXERCISE_ID = "ID_EXERCISE"
    }
}
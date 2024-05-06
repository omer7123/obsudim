package com.example.mypsychologist.ui.exercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentExercisesBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.setupSmallCard
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.exercises.REBTScreenState
import com.example.mypsychologist.presentation.exercises.REBTViewModel
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.cbt.TrackerMoodFragment
import com.example.mypsychologist.ui.exercises.rebt.ProblemsFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentExercises : Fragment() {

    private var binding: FragmentExercisesBinding by autoCleared()

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
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        binding.include.toolbar.apply {
            title = getString(R.string.exercises)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }


        setupCards()
        initListener()

        return binding.root
    }

    private fun render(it: REBTScreenState) {
        when (it) {
            is REBTScreenState.Data -> {
                binding.networkPlaceholder.layout.isVisible = false
                binding.placeholder.isVisible = false
           //     binding.content.isVisible = true
                setupData(it)
            }

            is REBTScreenState.Loading -> {
                if (!isNetworkConnect()) {
                    binding.networkPlaceholder.layout.isVisible = true
                    binding.placeholder.isVisible = false
              //      binding.content.isVisible = false
                }
            }

            is REBTScreenState.Empty -> {
                binding.networkPlaceholder.layout.isVisible = false
                binding.placeholder.isVisible = true
           //     binding.content.isVisible = false

            }

            is REBTScreenState.Error -> {
                binding.networkPlaceholder.layout.isVisible = false
                binding.placeholder.isVisible = false
           //     binding.content.isVisible = false
                requireContext().showToast(getString(R.string.db_error))
            }

            is REBTScreenState.Init -> Unit
        }
    }

    private fun setupData(it: REBTScreenState.Data) {
        setupCardsForREBT()
        it.problemProgress.apply {
            val problemTitle = "Проработка проблемы: $problem"
            binding.problemTitle.text = problemTitle

            val primaryCard =
                AppCompatResources.getDrawable(requireContext(), R.drawable.primary_card)

            if (problemAnalysisCompleted) {
                binding.problemAnalysis.card.background =
                    primaryCard

                binding.beliefsCheck.root.setOnClickListener {
                    findNavController().navigate(R.id.fragment_beliefs_verification)
                }
            }
            if (beliefsCheckCompleted) {
                binding.beliefsCheck.card.background =
                    primaryCard

                binding.beliefsAnalysis.root.setOnClickListener {
                    findNavController().navigate(R.id.fragment_beliefs_analysis)
                }
            }
            if (beliefsAnalysisCompleted) {
                binding.beliefsAnalysis.card.background =
                    primaryCard

                binding.dialog.root.setOnClickListener {
                    findNavController().navigate(R.id.autoDialogFragment)
                }
            }
            if (dialogCompleted)
                binding.dialog.card.background =
                    primaryCard
        }
    }


    private fun initListener() {
        with(binding) {
            diaryCard.root.setOnClickListener {
                findNavController().navigate(R.id.fragment_diaries)
            }

            trackerCard.root.setOnClickListener {
                val track = TrackerMoodFragment()
                track.show(childFragmentManager, "fs")
            }

            diaryNotesCard.root.setOnClickListener {
                findNavController().navigate(R.id.freeDiaryFragment)
            }

            binding.changeButton.setOnClickListener {
                setupChangeProblemFragment()
            }

            binding.beginButton.setOnClickListener {
                setupChangeProblemFragment()
            }

            binding.problemAnalysis.root.setOnClickListener {
                findNavController().navigate(R.id.fragment_rebt_harmful_thought)
            }
        }
    }

    private fun setupChangeProblemFragment() {
        childFragmentManager.setFragmentResultListener(
            ProblemsFragment.PROBLEM,
            viewLifecycleOwner
        ) { _, bundle ->
            viewModel.getProblemProgress(bundle.getString(ProblemsFragment.PROBLEM)!!)
        }

        ProblemsFragment().show(childFragmentManager, FragmentExercises.TAG)
    }


    private fun setupCards() {
        binding.apply {
            setupSmallCard(
                trackerCard,
                R.string.tracker_mood,
                R.drawable.ic_like
            )
            setupSmallCard(
                diaryCard,
                R.string.thought_diary,
                R.drawable.ic_diary
            )
            setupSmallCard(
                diaryNotesCard,
                R.string.diary_notes,
                R.drawable.ic_diary
            )
        }
    }

    private fun setupCardsForREBT() {
        binding.apply {
            setupSmallCard(
                problemAnalysis,
                R.string.problem_analysis,
                R.drawable.ic_problem_analysis
            )
            setupSmallCard(
                beliefsCheck,
                R.string.beliefs_check,
                R.drawable.ic_beliefs_check
            )
            setupSmallCard(
                beliefsAnalysis,
                R.string.beliefs_analysis,
                R.drawable.ic_beliefs_analysis
            )
            setupSmallCard(
                dialog,
                R.string.dialog_me,
                R.drawable.ic_dialog
            )
        }
    }
    companion object{
        const val TAG ="tag"
    }
}
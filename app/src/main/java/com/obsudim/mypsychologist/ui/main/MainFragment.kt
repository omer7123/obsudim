package com.obsudim.mypsychologist.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.databinding.FragmentMainBinding
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.main.mainFragment.MainEvent
import com.obsudim.mypsychologist.presentation.main.mainFragment.MainScreenState
import com.obsudim.mypsychologist.presentation.main.mainFragment.MainViewModel
import com.obsudim.mypsychologist.ui.diagnostics.passingTestFragment.PassingTestFragment
import com.obsudim.mypsychologist.ui.education.educationFragment.EducationFragment
import com.obsudim.mypsychologist.ui.exercises.newCbtDiaryFragment.FragmentNewCBTDiary
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = requireNotNull(_binding)

    @Inject
    lateinit var vmFactory: MainViewModel.Factory
    private val viewModel: MainViewModel by viewModels { vmFactory }

    private val adapter = DailyCardAdapter(this::clickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        setupListeners()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        initView()
        return binding.root
    }

    private fun initView() {
        binding.guidelineBottom.post {
            val lineBottom = binding.guidelineBottom.bottom
            val includeTop = binding.guidelineTop.bottom
            val bottomSheetBehavior: BottomSheetBehavior<LinearLayout> = BottomSheetBehavior.from(binding.bottomSheet)

            bottomSheetBehavior.peekHeight = lineBottom
            bottomSheetBehavior.maxHeight = binding.root.height - includeTop

            bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if(newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        bottomSheetBehavior.peekHeight = lineBottom
                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }

        binding.toolbar.toolbar.title = getString(R.string.tasks)
    }

    private fun render(state: MainScreenState) {
        when (state) {
            is MainScreenState.Error -> {
                binding.progressCircular.isVisible = false
            }
            MainScreenState.Initial -> {}
            MainScreenState.Loading -> {
                binding.exercisesRv.isVisible = false
                binding.progressCircular.isVisible = true
            }
            is MainScreenState.Content -> {
                binding.exercisesRv.isVisible = true
                binding.progressCircular.isVisible = false
                binding.exercisesRv.adapter = adapter
                adapter.submitList(state.tasks)

                binding.dateTv.text = state.date
            }
        }
    }

    private fun clickListener(dailyExerciseEntity: DailyExerciseEntity) {
        when (dailyExerciseEntity.type) {
            1 -> {
                findNavController().navigate(
                    R.id.action_main_fragment_to_reading_theory_graph,
                    bundleOf(
                        EducationFragment.TOPIC_TAG to dailyExerciseEntity.destinationId,
                        EducationFragment.TASK_ID to dailyExerciseEntity.id
                    )
                )
            }

            2 -> {
                if (dailyExerciseEntity.title == getString(R.string.tracker_mood_title)) {
                    findNavController().navigate(R.id.action_main_fragment_to_free_diary_graph)
                }
            }

            3 -> {
                findNavController().navigate(
                    R.id.action_main_fragment_to_passing_test_graph,
                    bundleOf(
                        PassingTestFragment.TEST_ID to dailyExerciseEntity.destinationId,
                        PassingTestFragment.TASK_ID to dailyExerciseEntity.id
                    )
                )
            }

            4 -> {
                findNavController().navigate(
                    R.id.fragment_new_diary,
                    bundleOf(
                        FragmentNewCBTDiary.EXERCISE_ID to dailyExerciseEntity.destinationId,
                        FragmentNewCBTDiary.TASK_ID to dailyExerciseEntity.id
                    )
                )
            }
        }
    }

    private fun setupListeners() {
        binding.apply {
            toolbar.profileIcon.setOnClickListener {
                findNavController().navigate(R.id.action_main_fragment_to_profile_graph)
            }
        }
    }

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.event.collect { event ->
                        when (event) {
                            is MainEvent.RequestNotificationPermission -> {
                                notificationPermissionLauncher.launch(
                                    Manifest.permission.POST_NOTIFICATIONS
                                )
                            }
                        }
                    }
                }

                val isGranted = ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

                viewModel.checkNotificationPermission(isGranted)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getInitialData()
    }

}
package com.example.mypsychologist.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentMainBinding
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.main.mainFragment.MainScreenState
import com.example.mypsychologist.presentation.main.mainFragment.MainViewModel
import com.example.mypsychologist.ui.diagnostics.passingTestFragment.PassingTestFragment
import com.example.mypsychologist.ui.education.educationFragment.EducationFragment
import com.example.mypsychologist.ui.exercises.newCbtDiaryFragment.FragmentNewCBTDiary
import com.example.mypsychologist.ui.exercises.trackerMoodBottomSheetFragment.TrackerMoodFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = requireNotNull(_binding)

    @Inject
    lateinit var vmFactory: MainViewModel.Factory
    private val viewModel: MainViewModel by viewModels { vmFactory }

    private val adapter = DailyCardAdapter(this::clickListener)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setNavbarActualItem()

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        setupListeners()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        initView()
        return binding.root
    }

    private fun setNavbarActualItem() {
//        if (activity is NavbarHider) {
//            (activity as NavbarHider).setActualItem(R.id.plan_item)
//        }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getInitialData()
    }

    private fun render(state: MainScreenState) {
        when (state) {
            is MainScreenState.Error -> {
                findNavController().navigate(R.id.registrationFragment)
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
                    R.id.action_main_fragment_to_education_nav,
                    bundleOf(
                        EducationFragment.TOPIC_TAG to dailyExerciseEntity.destinationId,
                        EducationFragment.TASK_ID to dailyExerciseEntity.id
                    )
                )
            }

            2 -> {
                if (dailyExerciseEntity.title == getString(R.string.tracker_mood_title)) {
                    val fragment = TrackerMoodFragment.newInstance(dailyExerciseEntity.id)
                    childFragmentManager.setFragmentResultListener(
                        TrackerMoodFragment.RESULT_KEY,
                        viewLifecycleOwner
                    ) { _, res ->
                        val key = res.getString(TrackerMoodFragment.RESULT_KEY)
                        if (key == TrackerMoodFragment.CLOSE)
                            viewModel.getInitialData()
                    }

                    fragment.show(childFragmentManager, TrackerMoodFragment.SHOW)

                } else {
                    //Вольный дневник
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
            toolbar.psychologistsIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_psychologists_with_tasks)
            }
        }
    }

}
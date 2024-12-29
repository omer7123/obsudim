package com.example.mypsychologist.ui.main

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
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
import com.example.mypsychologist.NavbarHider
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentMainBinding
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.core.BaseStateUI
import com.example.mypsychologist.presentation.main.mainFragment.MainViewModel
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.diagnostics.PassingTestFragment
import com.example.mypsychologist.ui.education.EducationFragment
import com.example.mypsychologist.ui.exercises.cbt.FragmentNewCBTDiary
import com.example.mypsychologist.ui.exercises.cbt.TrackerMoodFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Date
import javax.inject.Inject


class MainFragment : Fragment() {

    private var binding: FragmentMainBinding by autoCleared()

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

        if (activity is NavbarHider) {
            (activity as NavbarHider).setActualItem(R.id.plan_item)
        }

        binding = FragmentMainBinding.inflate(inflater, container, false)

        setupListeners()
        viewModel.screenState.flowWithLifecycle(lifecycle).onEach {
            render(it)
        }.launchIn(lifecycleScope)

        initView()
        return binding.root
    }

    private fun initView() {
        var formattedDate: String = DateFormat.format("EEEE, d MMMM", Date()).toString()
        val formattedDateSplit = formattedDate.split(" ").toMutableList()
        formattedDateSplit[2] = formattedDateSplit[2].lowercase()
        formattedDate = formattedDateSplit.joinToString(" ")
        binding.dateTv.text = formattedDate
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllExercises()
    }

    private fun render(state: BaseStateUI<List<DailyExerciseEntity>>) {
        when (state) {

            is BaseStateUI.Error -> {
                findNavController().navigate(R.id.registrationFragment)
            }

            is BaseStateUI.Initial -> {}
            is BaseStateUI.Loading -> {

                binding.exercisesRv.isVisible = false
                binding.progressCircular.isVisible = true
            }

            is BaseStateUI.Content -> {
                binding.exercisesRv.isVisible = true
                binding.progressCircular.isVisible = false

                binding.exercisesRv.adapter = adapter
                adapter.submitList(state.data)
            }
        }
    }

    private fun clickListener(dailyExerciseEntity: DailyExerciseEntity) {
        when (dailyExerciseEntity.type) {
            1 -> {
                findNavController().navigate(
                    R.id.fragment_education,
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
                    ){_, res->
                        val key = res.getString(TrackerMoodFragment.RESULT_KEY)
                        if (key == TrackerMoodFragment.CLOSE)
                            viewModel.getAllExercises()
                    }

                    fragment.show(childFragmentManager, TrackerMoodFragment.SHOW)

                } else {
                    //Вольный дневник
                }
            }

            3 -> {
                findNavController().navigate(
                    R.id.action_main_fragment_to_passingTestFragment,
                    bundleOf(
                        PassingTestFragment.TEST_ID to dailyExerciseEntity.destinationId,
                        PassingTestFragment.TASK_ID to dailyExerciseEntity.id
                    )
                )
            }

            4 -> {
                findNavController().navigate(
                    R.id.action_main_fragment_to_fragment_new_diary,
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
                findNavController().navigate(R.id.fragment_profile)
            }
            toolbar.psychologistsIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_psychologists_with_tasks)
            }
        }
    }

}
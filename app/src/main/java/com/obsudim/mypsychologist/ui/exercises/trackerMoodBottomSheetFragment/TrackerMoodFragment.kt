package com.obsudim.mypsychologist.ui.exercises.trackerMoodBottomSheetFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.obsudim.mypsychologist.databinding.FragmentTrackerMoodBinding
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.extensions.showToast
import com.obsudim.mypsychologist.presentation.di.MultiViewModelFactory
import com.obsudim.mypsychologist.presentation.exercises.freeDiaryWithTrackerMoodFragment.TrackerMoodViewModel
import com.obsudim.mypsychologist.presentation.exercises.trackerMoodFragment.TrackerMoodScreenState
import javax.inject.Inject


class TrackerMoodFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTrackerMoodBinding

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: TrackerMoodViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TrackerMoodViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackerMoodBinding.inflate(layoutInflater)
        viewModel.stateScreen.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun render(state: TrackerMoodScreenState) {
        when (state) {
            is TrackerMoodScreenState.Error -> {
                requireContext().showToast(state.msg)
                binding.progressBar.isVisible = false
                binding.saveBtn.isVisible = true
                binding.saveBtn.isClickable = true
            }

            TrackerMoodScreenState.Initial -> {}
            TrackerMoodScreenState.Loading -> {
                binding.progressBar.isVisible = true
                binding.saveBtn.visibility = View.INVISIBLE
                binding.saveBtn.isClickable = false
            }

            TrackerMoodScreenState.SuccessResp -> {
                dismiss()
            }

            is TrackerMoodScreenState.Content -> {
                binding.moodTv.text = getString(state.titleMoodResId)
            }
        }
    }

    private fun initListener() {
        binding.saveBtn.setOnClickListener {
            val dailyTaskId =
                if (arguments != null) arguments?.getString(DAILY_TASK_ID, "").toString()
                else ""
            viewModel.saveMood(binding.moodSb.value.toInt(), dailyTaskId)
        }
        binding.moodSb.addOnChangeListener{ _, value, _->
            viewModel.changeMoodInMainFragment(value)
        }
    }

    override fun dismiss() {
        super.dismiss()
        parentFragmentManager.setFragmentResult(RESULT_KEY, bundleOf(RESULT_KEY to CLOSE))
    }


    companion object {
        const val SHOW = "SHOW"
        const val CLOSE = "CLOSE"
        const val RESULT_KEY = "RESULT_KEY"
        private const val DAILY_TASK_ID = "DAILY_TASK_ID"

        fun newInstance(idTask: String): TrackerMoodFragment {
            return TrackerMoodFragment().apply {
                arguments = Bundle().apply {
                    putString(DAILY_TASK_ID, idTask)
                }
            }
        }
    }
}
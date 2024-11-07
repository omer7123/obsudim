package com.example.mypsychologist.ui.exercises.cbt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTrackerMoodBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.exercises.trackerMoodFragment.TrackerMoodScreenState
import com.example.mypsychologist.presentation.exercises.trackerMoodFragment.TrackerMoodViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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

    private fun render(state: TrackerMoodScreenState) {
        when (state) {
            is TrackerMoodScreenState.Error -> {
                requireContext().showToast(state.msg)
                binding.progressBar.isVisible = false
                binding.saveBtn.isVisible = true
                binding.specifyBtn.isVisible = true
                binding.saveBtn.isClickable = true
                binding.specifyBtn.isClickable = true
            }

            TrackerMoodScreenState.Initial -> {}
            TrackerMoodScreenState.Loading -> {
                binding.progressBar.isVisible = true
                binding.saveBtn.visibility = View.INVISIBLE
                binding.specifyBtn.visibility = View.INVISIBLE
                binding.saveBtn.isClickable = false
                binding.specifyBtn.isClickable = false
            }

            TrackerMoodScreenState.SuccessResp -> {
                dismiss()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.specifyBtn.setOnClickListener {
            findNavController().navigate(R.id.newFreeDiaryFragment)
        }
        binding.saveBtn.setOnClickListener {
            viewModel.saveMood(binding.moodSb.value.toInt())
        }
    }

    companion object{
        const val TAG = "TAG"
    }
}
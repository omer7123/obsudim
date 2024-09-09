package com.example.mypsychologist.ui.exercises.cbt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTrackerMoodBinding
import com.example.mypsychologist.presentation.authentication.authFragment.AuthViewModel
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class TrackerMoodFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTrackerMoodBinding

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackerMoodBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.specifyBtn.setOnClickListener {
            findNavController().navigate(R.id.newFreeDiaryFragment)
        }
    }

}
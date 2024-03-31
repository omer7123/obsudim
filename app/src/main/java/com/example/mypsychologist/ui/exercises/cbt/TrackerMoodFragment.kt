package com.example.mypsychologist.ui.exercises.cbt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTrackerMoodBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TrackerMoodFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTrackerMoodBinding

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
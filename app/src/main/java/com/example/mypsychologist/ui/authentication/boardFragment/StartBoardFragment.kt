package com.example.mypsychologist.ui.authentication.boardFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentStartBoardBinding

class StartBoardFragment : Fragment() {
    private var _binding: FragmentStartBoardBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBoardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.startBtn.setOnClickListener {
            findNavController().navigate(R.id.action_startBoardFragment_to_firstBoardFragment)
        }
        binding.skipBtn.setOnClickListener {
            findNavController().navigate(R.id.action_startBoardFragment_to_main_fragment)
        }
    }
}
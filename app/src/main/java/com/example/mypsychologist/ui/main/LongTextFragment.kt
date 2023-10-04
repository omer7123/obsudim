package com.example.mypsychologist.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.databinding.FragmentLongTextBinding
import com.example.mypsychologist.ui.autoCleared

class LongTextFragment : Fragment() {

    private var binding: FragmentLongTextBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLongTextBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = requireArguments().getString(TITLE)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.textView.text = getText(requireArguments().getInt(TEXT_ID))

        return binding.root
    }

    companion object {
        const val TITLE = "title"
        const val TEXT_ID = "text id"
    }
}
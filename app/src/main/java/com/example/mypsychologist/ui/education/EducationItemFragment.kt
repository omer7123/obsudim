package com.example.mypsychologist.ui.education

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.ItemEducationBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.education.EducationViewModel
import com.example.mypsychologist.ui.autoCleared
import javax.inject.Inject

class EducationItemFragment : Fragment() {

    private var binding: ItemEducationBinding by autoCleared()

    @Inject
    lateinit var vmFactory: EducationViewModel.Factory
    private val viewModel: EducationViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireContext().getAppComponent().educationComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemEducationBinding.inflate(inflater, container, false)

        viewModel.saveProgress(
            requireArguments().getString(MATERIAL_ID).toString()
        )

        setupFields(
            requireArguments().getInt(CURRENT),
            requireArguments().getInt(COUNT),
            requireArguments().getString(TEXT)!!
        )

        binding.exitButton.setOnClickListener {
            findNavController().navigate(R.id.fragment_education_topics)
        }

        return binding.root
    }

    private fun setupFields(current: Int, count: Int, text: String) {
        binding.apply {
            textView.text = text
            progress.text = getString(
                R.string.test_progress,
                (current + 1).toString(),
                count.toString()
            )

            if (current == 0)
                swipeIcon.isVisible = true

            if (current + 1 == count)
                exitButton.isVisible = true
        }
    }

    companion object {
        fun newInstance(current: Int, count: Int, text: String, id: String) =
            EducationItemFragment().apply {
                arguments = bundleOf(
                    CURRENT to current,
                    COUNT to count,
                    TEXT to text,
                    MATERIAL_ID to id
                )
            }

        private const val CURRENT = "current"
        private const val COUNT = "count"
        private const val TEXT = "text"
        private const val MATERIAL_ID = "id_material"
    }
}
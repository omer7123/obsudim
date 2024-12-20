package com.example.mypsychologist.ui.psychologist

import android.content.Context
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.*
import com.example.mypsychologist.databinding.FragmentPsychologistBinding
import com.example.mypsychologist.domain.entity.PsychologistInfo
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.extensions.toYears
import com.example.mypsychologist.presentation.psychologist.PsychologistScreenState
import com.example.mypsychologist.presentation.psychologist.PsychologistViewModel
import com.example.mypsychologist.ui.StringAdapter
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

class PsychologistFragment : Fragment() {

    private var binding: FragmentPsychologistBinding by autoCleared()

    @Inject
    lateinit var vmFactory: PsychologistViewModel.Factory
    private val viewModel: PsychologistViewModel by viewModels {
        PsychologistViewModel.provideFactory(
            vmFactory,
            requireArguments().getString(ID)!!
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireContext().getAppComponent().psychologistComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPsychologistBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.psychologist)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.fragment_request_to_psychologist, bundleOf(
                RequestToPsychologistFragment.PSYCHOLOGIST_ID to requireArguments().getString(ID)!!
            ))
        }

        return binding.root
    }

    private fun render(state: PsychologistScreenState) {
        when (state) {
            is PsychologistScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    requireContext().showToast(getString(R.string.network_error))
            }
            is PsychologistScreenState.Data -> {
                binding.progressBar.isVisible = false
                setupFields(state.psychologist.info)
            }
            is PsychologistScreenState.Error -> {
                requireContext().showToast(getString(R.string.db_error))
            }
            is PsychologistScreenState.Init -> Unit
        }
    }

    private fun setupFields(info: PsychologistInfo) {
        binding.apply {
            name.text = info.name 
            genderAndAge.text = getString(
                R.string.gender_and_age,
                info.gender,
                (Date().time - info.birthday).toYears().toString()
            )
            city.text = info.city
            education.text = info.education
            format.text = info.formats
            setupChips(info.specialization)
            aboutText.text = info.about
            setupAdapter(info.courses)
        }
    }

    private fun setupChips(list: List<String>) {
        list.forEach { specialization ->
            binding.specializationsGroup.addView(
                Chip(requireContext()).apply {
                    text = specialization
                    isClickable = false
                }
            )
        }
    }

    private fun setupAdapter(list: List<String>) {
        binding.coursesRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = StringAdapter(list)
            setHasFixedSize(true)
        }
    }

    companion object {
        const val ID = "id"
    }
}
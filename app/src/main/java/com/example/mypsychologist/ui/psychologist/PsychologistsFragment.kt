package com.example.mypsychologist.ui.psychologist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentPsychologistsBinding
import com.example.mypsychologist.domain.entity.PsychologistCard
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.psychologist.PsychologistsScreenState
import com.example.mypsychologist.presentation.psychologist.PsychologistsViewModel
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PsychologistsFragment : Fragment() {

    private var binding: FragmentPsychologistsBinding by autoCleared()

    @Inject
    lateinit var vmFactory: PsychologistsViewModel.Factory
    private val viewModel: PsychologistsViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().psychologistComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPsychologistsBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.title = getString(R.string.psychologists)
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun render(state: PsychologistsScreenState) {
        when(state) {
            is PsychologistsScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    requireContext().showToast(getString(R.string.network_error))
            }
            is PsychologistsScreenState.Data -> {
                binding.progressBar.isVisible = false
                setupAdapter(state.items.toList())
            }
            is PsychologistsScreenState.Error -> {
                requireContext().showToast(getString(R.string.db_error))
            }
            is PsychologistsScreenState.Init -> Unit
        }
    }

    private fun setupAdapter(list: List<Pair<String, PsychologistCard>>) {
        binding.psychologistsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = PsychologistCardsAdapter(list) { id ->
//                findNavController().navigate(R.id.fragment_psychologist, bundleOf(
//                    PsychologistFragment.ID to id
//                ))
            }
            setHasFixedSize(true)
        }
    }
}
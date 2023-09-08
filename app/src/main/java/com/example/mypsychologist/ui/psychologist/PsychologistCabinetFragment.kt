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
import com.example.mypsychologist.*
import com.example.mypsychologist.databinding.FragmentClientRequestBinding
import com.example.mypsychologist.databinding.FragmentPsychologistCabinetBinding
import com.example.mypsychologist.presentation.PsychologistCabinetScreenState
import com.example.mypsychologist.presentation.PsychologistCabinetViewModel
import com.example.mypsychologist.presentation.PsychologistsScreenState
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PsychologistCabinetFragment : Fragment() {

    private var binding: FragmentPsychologistCabinetBinding by autoCleared()

    @Inject
    lateinit var vmFactory: PsychologistCabinetViewModel.Factory
    private val viewModel: PsychologistCabinetViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().psychologistComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPsychologistCabinetBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.psychologist_cabinet)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        setupCards()
        setupListeners()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun setupCards() {
        binding.apply {
            setupCard(messagesCard, R.string.chat, R.string.chat, R.drawable.ic_forum)
            setupCard(clientsCard, R.string.clients, R.string.clients, R.drawable.ic_diversity_3)
            setupCard(
                calendarCard,
                R.string.schedule,
                R.string.schedule,
                R.drawable.ic_calendar_month
            )
            setupCard(notesCard, R.string.notes, R.string.notes, R.drawable.ic_description)
        }
    }

    private fun setupListeners() {
        binding.apply {
            clientsCard.card.setOnClickListener {
                findNavController().navigate(R.id.fragment_clients)
            }
        }
    }

    private fun render(state: PsychologistCabinetScreenState) {
        when (state) {
            is PsychologistCabinetScreenState.Loading -> {
                if (!isNetworkConnect())
                    showToast(getString(R.string.network_error))
            }
            is PsychologistCabinetScreenState.Data -> {
                if (state.requests.isNotEmpty())
                    binding.requests.isVisible = true

                binding.requestsVp.apply {
                    clipChildren = false
                    clipToPadding = false
                    offscreenPageLimit = 3
                    adapter = ClientRequestsAdapter(state.requests) { clientId ->
                        findNavController().navigate(R.id.fragment_client_request, bundleOf(
                            ClientRequestFragment.REQUEST to state.requests.find { it.clientId == clientId }
                        ))
                    }
                }
            }
            is PsychologistCabinetScreenState.Error -> {
                showToast(getString(R.string.db_error))
            }
            is PsychologistCabinetScreenState.Init -> Unit
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRequests()
    }
}
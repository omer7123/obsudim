package com.example.mypsychologist.ui.main

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
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentPsychologistsBinding
import com.example.mypsychologist.domain.entity.ClientCardEntity
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.main.ClientsScreenState
import com.example.mypsychologist.presentation.main.ClientsViewModel
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ClientsFragment : Fragment() {

    private var binding: FragmentPsychologistsBinding by autoCleared()

    @Inject
    lateinit var vmFactory: ClientsViewModel.Factory
    private val viewModel: ClientsViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPsychologistsBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.title = getString(R.string.clients)
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun render(state: ClientsScreenState) {
        when (state) {
            is ClientsScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    showToast(getString(R.string.network_error))
            }
            is ClientsScreenState.Data -> {
                binding.progressBar.isVisible = false
                setupAdapter(state.items)
            }
            is ClientsScreenState.Error -> {
                showToast(getString(R.string.db_error))
            }
            is ClientsScreenState.Init -> Unit
        }
    }

    private fun setupAdapter(list: List<ClientCardEntity>) {
        binding.psychologistsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ClientCardsAdapter(list) { id ->
                findNavController().navigate(
                    R.id.fragment_client_info, bundleOf(
                        ClientInfoFragment.ID to id
                    )
                )
            }
            setHasFixedSize(true)
        }
    }
}
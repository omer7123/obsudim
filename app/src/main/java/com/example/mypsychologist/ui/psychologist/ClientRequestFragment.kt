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
import com.example.mypsychologist.domain.entity.ClientRequestEntity
import com.example.mypsychologist.presentation.psychologist.AnswerScreenState
import com.example.mypsychologist.presentation.main.ClientRequestViewModel
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.main.ClientInfoFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ClientRequestFragment : Fragment() {

    private var binding: FragmentClientRequestBinding by autoCleared()

    @Inject
    lateinit var vmFactory: ClientRequestViewModel.Factory
    private val viewModel: ClientRequestViewModel by viewModels { vmFactory }

    private lateinit var request: ClientRequestEntity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().psychologistComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClientRequestBinding.inflate(inflater, container, false)

       setupFields()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        setupListeners()

        return binding.root
    }

    private fun setupFields() {
        request = requireArguments().parcelable(REQUEST)!!

        binding.apply {
            includeToolbar.toolbar.apply {
                title = getString(R.string.request)
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
            }

            liter.text = request.name[0].toString()
            name.text = request.name
            text.text = request.text
        }
    }

    private fun render(state: AnswerScreenState) {
        when(state) {
            is AnswerScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    showToast(getString(R.string.network_error))
            }
            is AnswerScreenState.Response -> {
                binding.progressBar.isVisible = false
                if (state.result) {
                    showToast(getString(R.string.success))
                    findNavController().popBackStack()
                }
                else
                    showToast(getString(R.string.db_error))
            }
            is AnswerScreenState.Init -> Unit
        }
    }

    private fun setupListeners() {
        binding.apply {

            literLayout.setOnClickListener { goToClient() }
            name.setOnClickListener { goToClient() }

            acceptButton.setOnClickListener {
                viewModel.sendAnswer(true, request.clientId)
            }
            rejectionButton.setOnClickListener {
                viewModel.sendAnswer(false, request.clientId)
            }
        }
    }

    private fun goToClient() {
        findNavController().navigate(R.id.fragment_client_info, bundleOf(
            ClientInfoFragment.ID to request.clientId
        ))
    }

    companion object {
        const val REQUEST = "request"
    }
}
package com.example.mypsychologist.ui.psychologist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentRequestToPsychologistBinding
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.main.FeedbackScreenState
import com.example.mypsychologist.presentation.psychologist.RequestToPsychologistViewModel
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RequestToPsychologistFragment : Fragment() {

    private var binding: FragmentRequestToPsychologistBinding by autoCleared()

    @Inject
    lateinit var vmFactory: RequestToPsychologistViewModel.Factory
    private val viewModel: RequestToPsychologistViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().psychologistComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestToPsychologistBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.request)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        binding.sendButton.setOnClickListener {
            viewModel.tryToSendRequest(
                binding.field.text.toString(),
                requireArguments().getString(PSYCHOLOGIST_ID)!!
            )
        }

        binding.field.addTextChangedListener {
            binding.inputLayout.error = null
        }

        return binding.root
    }

    private fun render(state: FeedbackScreenState) {
        when (state) {
            is FeedbackScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    showToast(getString(R.string.network_error))
            }
            is FeedbackScreenState.Response -> {
                binding.progressBar.isVisible = false
                if (state.result) {
                    showToast(getString(R.string.success))
                    findNavController().popBackStack()
                } else {
                    showToast(getString(R.string.db_error))
                }
            }
            is FeedbackScreenState.ValidationError -> {
                binding.inputLayout.error = getString(R.string.necessary_to_fill)
            }
            is FeedbackScreenState.Init -> Unit
        }
    }

    companion object {
        const val PSYCHOLOGIST_ID = "psychologist_id"
    }
}
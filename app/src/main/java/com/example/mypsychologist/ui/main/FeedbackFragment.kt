package com.example.mypsychologist.ui.main

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
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentFeedbackBinding
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.FeedbackScreenState
import com.example.mypsychologist.presentation.FeedbackViewModel
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FeedbackFragment : Fragment() {

    private var binding: FragmentFeedbackBinding by autoCleared()

    @Inject
    lateinit var vmFactory: FeedbackViewModel.Factory
    private val viewModel: FeedbackViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFeedbackBinding.inflate(inflater, container, false)

        binding.include.toolbar.apply {
            title = getString(R.string.feedback)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        binding.sendButton.setOnClickListener {
            viewModel.tryToSendFeedback(binding.field.text.toString())
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

}
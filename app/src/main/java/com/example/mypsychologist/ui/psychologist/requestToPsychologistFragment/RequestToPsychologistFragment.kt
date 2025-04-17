package com.example.mypsychologist.ui.psychologist.requestToPsychologistFragment

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
import com.example.mypsychologist.NavbarHider
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentRequestToPsychologistBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.profile.feedBackFragment.FeedbackScreenState
import com.example.mypsychologist.presentation.psychologist.requestToPsychologistFragment.RequestToPsychologistViewModel
import com.example.mypsychologist.ui.core.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RequestToPsychologistFragment : Fragment() {

    private var binding: FragmentRequestToPsychologistBinding by autoCleared()
    private var navbarHider: NavbarHider? = null

    @Inject
    lateinit var vmFactory: RequestToPsychologistViewModel.Factory
    private val viewModel: RequestToPsychologistViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)

        if (context is NavbarHider) {
            navbarHider = context
            navbarHider!!.setNavbarVisibility(false)
        }
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

        binding.goToProfileButton.setOnClickListener {
            findNavController().navigate(R.id.fragment_edit)
        }

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

    override fun onStart() {
        super.onStart()
        viewModel.getUserData()
    }

    private fun render(state: FeedbackScreenState) {
        when (state) {
            is FeedbackScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    requireContext().showToast(getString(R.string.network_error))
            }

            is FeedbackScreenState.UserNameSaved -> {
                binding.progressBar.isVisible = false
                if (!state.result) {
                    binding.groupPlaceholder.isVisible = true
                    binding.groupContent.isVisible = false
                }
            }

            is FeedbackScreenState.Response -> {
                binding.progressBar.isVisible = false
                if (state.result) {
                    requireContext().showToast(getString(R.string.success))
                    findNavController().popBackStack()
                } else {
                    requireContext().showToast(getString(R.string.db_error))
                }
            }

            is FeedbackScreenState.ValidationError -> {
                binding.inputLayout.error = getString(R.string.necessary_to_fill)
            }

            is FeedbackScreenState.Init -> Unit
        }
    }

    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }

    companion object {
        const val PSYCHOLOGIST_ID = "psychologist_id"
    }
}
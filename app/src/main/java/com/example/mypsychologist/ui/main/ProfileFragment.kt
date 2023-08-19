package com.example.mypsychologist.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.MainActivity
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentProfileBinding
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.presentation.ProfileViewModel
import com.example.mypsychologist.ui.ConfirmationDialogFragment
import com.example.mypsychologist.ui.autoCleared
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding by autoCleared()

    @Inject
    lateinit var vmFactory: ProfileViewModel.Factory
    private val viewModel: ProfileViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.include.toolbar.title = getString(R.string.profile)

        setupListeners()

        viewModel.goToAuthorization
            .flowWithLifecycle(lifecycle)
            .onEach {
                if (it)
                    startMainActivity()
             }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            include.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            editData.setOnClickListener {
                findNavController().navigate(R.id.fragment_edit)
            }

            forPsychologist.setOnClickListener {
                findNavController().navigate(R.id.fragment_psychologist_questionnaire)
            }

            rules.setOnClickListener {
                findNavController().navigate(R.id.fragment_long_text, bundleOf(
                    LongTextFragment.TITLE to rules.text.toString(),
                    LongTextFragment.TEXT_ID to R.string.rules_text
                ))
            }

            feedback.setOnClickListener {
                findNavController().navigate(R.id.fragment_feedback)
            }

            exitButton.setOnClickListener {
                signOut()
            }

            deleteAccountButton.setOnClickListener {
                deleteAccount()
            }
        }
    }

    private fun signOut() {
        childFragmentManager.setFragmentResultListener(
            EXIT,
            viewLifecycleOwner
        ) { _, bundle ->
            if (bundle.getBoolean(ConfirmationDialogFragment.RESULT)) {
                viewModel.signOut()
            }
        }

        ConfirmationDialogFragment.newInstance(R.string.confirm_exit)
            .show(childFragmentManager, EXIT)
    }

    private fun deleteAccount() {
        childFragmentManager.setFragmentResultListener(
            DELETE,
            viewLifecycleOwner
        ) { _, bundle ->
            if (bundle.getBoolean(ConfirmationDialogFragment.RESULT)) {
                viewModel.deleteAccount()
            }
        }

        ConfirmationDialogFragment.newInstance(R.string.confirm_delete)
            .show(childFragmentManager, DELETE)
    }

    private fun startMainActivity() {
        val newIntent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        requireActivity().startActivity(newIntent)
    }

    companion object {
        private const val EXIT = "exit"
        private const val DELETE = "delete"
    }
}
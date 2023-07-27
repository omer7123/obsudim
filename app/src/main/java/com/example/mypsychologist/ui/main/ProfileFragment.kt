package com.example.mypsychologist.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentProfileBinding
import com.example.mypsychologist.ui.ConfirmationDialogFragment
import com.example.mypsychologist.ui.autoCleared

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.include.toolbar.title = getString(R.string.profile)

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            include.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            exitButton.setOnClickListener {

                childFragmentManager.setFragmentResultListener(
                    EXIT,
                    viewLifecycleOwner
                ) { _, bundle ->
                    if (bundle.getBoolean(ConfirmationDialogFragment.RESULT)) {

                    }
                }

                ConfirmationDialogFragment.newInstance(R.string.confirm_exit)
                    .show(childFragmentManager, EXIT)
            }

            deleteAccountButton.setOnClickListener {

                childFragmentManager.setFragmentResultListener(
                    DELETE,
                    viewLifecycleOwner
                ) { _, bundle ->
                    if (bundle.getBoolean(ConfirmationDialogFragment.RESULT)) {

                    }
                }

                ConfirmationDialogFragment.newInstance(R.string.confirm_delete)
                    .show(childFragmentManager, DELETE)
            }
        }
    }

    companion object {
        private const val EXIT = "exit"
        private const val DELETE = "delete"
    }
}
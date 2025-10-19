package com.obsudim.mypsychologist.ui.profile.profileFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.obsudim.mypsychologist.MainActivity
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.databinding.FragmentProfileBinding
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.profile.profileFragment.ProfileViewModel
import com.obsudim.mypsychologist.ui.core.ConfirmationDialogFragment
import com.obsudim.mypsychologist.ui.core.autoCleared
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


//            rules.setOnClickListener {
//                findNavController().navigate(R.id.fragment_long_text, bundleOf(
//                    LongTextFragment.TITLE to rules.text.toString(),
//                    LongTextFragment.TEXT_ID to R.string.rules_text
//                ))
//            }

            feedback.setOnClickListener {
                findNavController().navigate(R.id.fragment_feedback)
            }

            exitButton.setOnClickListener {
                signOut()
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


    private fun startMainActivity() {
        val newIntent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        requireActivity().startActivity(newIntent)
    }

    companion object {
        private const val EXIT = "exit"
    }
}
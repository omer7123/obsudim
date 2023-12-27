package com.example.mypsychologist.ui.exercises.rebt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentAutoDialogBinding
import com.example.mypsychologist.domain.entity.AutoDialogMessageEntity
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.ListScreenState
import com.example.mypsychologist.presentation.exercises.AutoDialogViewModel
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AutoDialogFragment : Fragment() {

    private var binding: FragmentAutoDialogBinding by autoCleared()

    @Inject
    lateinit var vmFactory: AutoDialogViewModel.Factory
    private val viewModel: AutoDialogViewModel by viewModels { vmFactory }

    private lateinit var mainAdapter: MainAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAutoDialogBinding.inflate(inflater, container, false)

        binding.toolbar.toolbar.apply {
            title = getString(R.string.dialog)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        setupAdapter()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        binding.sendButton.setOnClickListener {
            if (binding.message.text.isNotEmpty()) {
                viewModel.save(binding.message.text.toString())
                binding.message.setText("")
                showMessageType()
            }
        }

        return binding.root
    }

    private fun setupAdapter() {
        binding.messagesRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            mainAdapter = MainAdapter().apply {
                addDelegate(
                    AutoDialogDelegate()
                )
            }
            adapter = mainAdapter
        }
    }

    private fun render(state: ListScreenState) {
        when (state) {
            is ListScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    showToast(getString(R.string.network_error))
            }

            is ListScreenState.Data -> {
                binding.progressBar.isVisible = false
                mainAdapter.submitList(state.items)
                showMessageType()
            }

            is ListScreenState.Error -> {
                binding.progressBar.isVisible = false
                showToast(getString(R.string.db_error))
            }

            is ListScreenState.Init -> Unit
        }
    }

    private fun showMessageType() {
        binding.messageType.text =
            getString(
                if (viewModel.currentIsRational())
                    R.string.rational
                else
                    R.string.irrational
            )
    }
}
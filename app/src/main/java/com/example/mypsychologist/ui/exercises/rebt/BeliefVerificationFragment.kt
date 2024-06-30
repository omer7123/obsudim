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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.databinding.FragmentBeliefVerificationBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.presentation.exercises.BeliefVerificationScreenState
import com.example.mypsychologist.presentation.exercises.BeliefVerificationViewModel
import com.example.mypsychologist.extensions.renderRebtBeliefType
import com.example.mypsychologist.extensions.showHint
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.cbt.InputDelegate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class BeliefVerificationFragment : Fragment() {

    private var binding: FragmentBeliefVerificationBinding by autoCleared()

    @Inject
    lateinit var vmFactory: BeliefVerificationViewModel.Factory
    private val viewModel: BeliefVerificationViewModel by viewModels {
        BeliefVerificationViewModel.provideFactory(
            vmFactory, requireArguments().getString(
                TYPE, ""
            )
        )
    }

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
        binding = FragmentBeliefVerificationBinding.inflate(inflater, container, false)

        setupFields()
        setupAdapter(viewModel.items)

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        binding.saveButton.setOnClickListener {
            viewModel.tryToSaveBeliefVerification(requireArguments().getString(TYPE)!!)
        }

        return binding.root
    }

    private fun setupFields() {
        binding.beliefType.text = renderRebtBeliefType(requireArguments().getString(TYPE)!!)
        binding.belief.text = requireArguments().getString(BELIEF)
    }

    private fun setupAdapter(items: List<DelegateItem>) {
        mainAdapter = MainAdapter().apply {
            addDelegate(
                InputDelegate(::showHint)
            )
            submitList(items)
        }

        binding.questionsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())

            adapter = mainAdapter
        }
    }


    private fun render(state: BeliefVerificationScreenState) {
        when (state) {
            is BeliefVerificationScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    requireContext().showToast(getString(R.string.network_error))
            }

            is BeliefVerificationScreenState.Data -> {
                binding.progressBar.isVisible = false
                mainAdapter.submitList(state.saved)
            }

            is BeliefVerificationScreenState.RequestResult -> {
                binding.progressBar.isVisible = false
                render(state.success)
            }

            is BeliefVerificationScreenState.ValidationError -> {
                mainAdapter.submitList(state.listWithErrors)
            }

            is BeliefVerificationScreenState.Init -> Unit
            is BeliefVerificationScreenState.Error -> {
                requireContext().showToast(state.message)
            }
        }
    }

    private fun render(resource: Resource<String>) {

        when (resource) {

            is Resource.Loading -> {}
            is Resource.Success -> {
                requireContext().showToast(getString(R.string.success))
            }
            is Resource.Error -> {
                requireContext().showToast(
                    getString(
                        if (isNetworkConnect())
                            R.string.db_error
                        else
                            R.string.network_error
                    )
                )
            }

        }
    }

    private fun setResult() {
        setFragmentResult(FINISH, bundleOf())
    }

    companion object {
        const val FINISH = "finish"
        private const val TYPE = "type"
        private const val BELIEF = "belief"

        fun newInstance(type: String, belief: String) =
            BeliefVerificationFragment().apply {
                arguments = bundleOf(TYPE to type, BELIEF to belief)
            }
    }
}
package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestBinding
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.BeckDepressionScreenState
import com.example.mypsychologist.presentation.BeckDepressionTestViewModel
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentBeckDepressionTest : Fragment() {

    private var binding: FragmentTestBinding by autoCleared()

    @Inject
    lateinit var vmFactory: BeckDepressionTestViewModel.Factory
    private val viewModel: BeckDepressionTestViewModel by viewModels{ vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().diagnosticComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)

        setupData()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        setFragmentResultListeners()

        return binding.root
    }

    private fun setupData() {
        binding.apply {
            includeToolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            title.text = getString(R.string.depression_beck_test)
            text.text = getString(R.string.depression_beck_test_desc)
        }
    }

    private fun render(state: BeckDepressionScreenState) {
        when (state) {
            is BeckDepressionScreenState.Question -> {
                FragmentTestQuestion().apply {
                    arguments = bundleOf(
                        FragmentTestQuestion.ANSWER_VARIANTS to state.answerVariants,
                        FragmentTestQuestion.IS_FIRST to (state.number == 0)
                    )
                    isCancelable = false
                }.show(childFragmentManager, TAG)
            }
            is BeckDepressionScreenState.Result -> {
                if (!isNetworkConnect()) {
                    Snackbar.make(
                        binding.coordinator,
                        R.string.save_after_connect,
                        Snackbar.LENGTH_LONG
                    ).setAction(R.string.show_result) {
                        showResult(state)
                    }.show()
                }
                else {
                    showResult(state)
                }
            }
            is BeckDepressionScreenState.Error -> {
                showToast(getString(R.string.db_error))
            }
        }
    }

    private fun showResult(it: BeckDepressionScreenState.Result) {
        TestResultDialogFragment.newInstance(it.score, getString(it.conclusionId), R.string.depression_beck_test)
            .show(childFragmentManager, TestResultDialogFragment.TAG)
    }

    private fun setFragmentResultListeners() {
        childFragmentManager.setFragmentResultListener(
            FragmentTestQuestion.ANSWER,
            viewLifecycleOwner
        ) { _, bundle ->
            viewModel.saveAnswerAndGoToNext(bundle.getInt(FragmentTestQuestion.SCORE))
        }

        childFragmentManager.setFragmentResultListener(
            FragmentTestQuestion.GO_BACK,
            viewLifecycleOwner
        ) { _, _ ->
            viewModel.lastQuestion()
        }
    }

    companion object {
        private const val TAG = "tag"
    }
}
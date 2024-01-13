package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestBinding
import com.example.mypsychologist.domain.entity.DASSResultEntity
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.diagnostics.CMQScreenState
import com.example.mypsychologist.presentation.diagnostics.DASSScreenState
import com.example.mypsychologist.presentation.diagnostics.DASSTestViewModel
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DASSTestFragment : Fragment() {
    private var binding: FragmentTestBinding by autoCleared()

    @Inject
    lateinit var vmFactory: DASSTestViewModel.Factory
    private val viewModel: DASSTestViewModel by viewModels { vmFactory }

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
            title.text = getString(R.string.dass21)
            text.text = getString(R.string.dass21_desc)
        }
    }

    private fun render(state: DASSScreenState) {
        when (state) {
            is DASSScreenState.Question -> {
                FragmentTestQuestion.newInstance(
                    state.answerVariants,
                    state.number + 1,
                    state.count
                ).show(childFragmentManager, TAG)
            }

            is DASSScreenState.Result -> {
                viewModel.save(state.result)

                if (!isNetworkConnect()) {
                    Snackbar.make(
                        binding.coordinator,
                        R.string.save_after_connect,
                        Snackbar.LENGTH_LONG
                    ).setAction(R.string.show_result) {
                        showResult(state)
                    }.show()
                } else {
                    showResult(state)
                }
            }

            is DASSScreenState.Error -> {
                showToast(getString(R.string.db_error))
            }
        }
    }

    private fun showResult(it: DASSScreenState.Result) {
        TestScalesResultFragment.newInstance(
            R.string.dass21,
            it.result.toHashMap()
        )
            .show(childFragmentManager, TestResultDialogFragment.TAG)
    }

    private fun DASSResultEntity.toHashMap() =
        hashMapOf(
            R.string.stress to stressScoreAndConclusion,
            R.string.anxiety to anxietyScoreAndConclusion,
            R.string.depression to depressionScoreAndConclusion
        )

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
            viewModel.previousQuestion()
        }
    }

    companion object {
        private const val TAG = "tag"
    }
}
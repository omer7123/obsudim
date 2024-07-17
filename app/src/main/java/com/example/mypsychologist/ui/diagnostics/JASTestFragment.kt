package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentJASTestBinding
import com.example.mypsychologist.domain.entity.JASResultEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.diagnostics.JASScreenState
import com.example.mypsychologist.presentation.diagnostics.JASTestViewModel
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class JASTestFragment : Fragment() {

    private var binding: FragmentJASTestBinding by autoCleared()

    @Inject
    lateinit var vmFactory: JASTestViewModel.Factory
    private val viewModel: JASTestViewModel by viewModels { vmFactory }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().diagnosticComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJASTestBinding.inflate(layoutInflater)

        setupData()
        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach {
                render(it)
            }
            .launchIn(lifecycleScope)

        setFragmentResultListeners()
        return binding.root
    }

    private fun setupData() {
        binding.apply {
            includeToolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            title.text = getString(R.string.jas)
            text.text = getString(R.string.jas_desc)
        }
    }

    private fun render(state: JASScreenState) {
        when (state) {
            is JASScreenState.Question -> {
                FragmentTestQuestion.newInstance(
                    state.answerVariants,
                    state.number + 1,
                    state.count
                ).show(childFragmentManager, TAG)
            }

            is JASScreenState.Result -> {
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

            is JASScreenState.Error -> {
                requireContext().showToast(getString(R.string.db_error))
            }
        }
    }

    private fun showResult(it: JASScreenState.Result) {
        TestScalesResultFragment.newInstance(
            R.string.jas,
            it.result.toHashMap()
        )
            .show(childFragmentManager, TestResultDialogFragment.TAG)
    }

    private fun JASResultEntity.toHashMap() =
        hashMapOf(
            R.string.apathetic_actions to apatheticActions,
            R.string.apathetic_thoughts to apatheticThoughts
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
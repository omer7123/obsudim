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
import com.example.mypsychologist.databinding.FragmentMBITestBinding
import com.example.mypsychologist.domain.entity.MBIResultEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.diagnostics.MBIScreenState
import com.example.mypsychologist.presentation.diagnostics.MBITestViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MBITestFragment : Fragment() {
    private lateinit var binding: FragmentMBITestBinding

    @Inject
    lateinit var vmFactory: MBITestViewModel.Factory
    private val viewModel: MBITestViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().diagnosticComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMBITestBinding.inflate(layoutInflater)
        setupData()
        viewModel.screenState.flowWithLifecycle(lifecycle).onEach {
            render(it)
        }.launchIn(lifecycleScope)

        setFragmentResultListener()
        return binding.root
    }


    private fun setupData() {
        binding.apply {
            includeToolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            title.text = getString(R.string.mbi)
            text.text = getString(R.string.mbi_desc)
        }
    }

    private fun render(state: MBIScreenState) {
        when (state) {
            is MBIScreenState.Question -> {
//                FragmentTestQuestion.newInstance(
//                    state.answerVariants,
//                    state.number + 1,
//                    state.count
//                ).show(childFragmentManager, MBITestFragment.TAG)
            }

            is MBIScreenState.Result -> {
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

            is MBIScreenState.Error -> {
                requireContext().showToast(getString(R.string.db_error))
            }
        }
    }

    private fun showResult(it: MBIScreenState.Result) {
        TestScalesResultFragment.newInstance(
            R.string.mbi,
            it.result.toHashMap()
        )
            .show(childFragmentManager, TestResultDialogFragment.TAG)
    }

    private fun MBIResultEntity.toHashMap() =
        hashMapOf(
            R.string.emotional_exhaustion to emotionalExhaustion,
            R.string.depersonalization to depersonalization,
            R.string.reduction_of_personal_achievements to reductionOfPersonalAchievements,

        )

    private fun setFragmentResultListener() {
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
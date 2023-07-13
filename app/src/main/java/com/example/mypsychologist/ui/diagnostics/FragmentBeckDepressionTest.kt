package com.example.mypsychologist.ui.diagnostics

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
import com.example.mypsychologist.presentation.BeckDepressionScreenState
import com.example.mypsychologist.presentation.BeckDepressionTestViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FragmentBeckDepressionTest : Fragment() {

    private lateinit var binding: FragmentTestBinding
    private val viewModel: BeckDepressionTestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)

        binding.apply {
            includeToolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            title.text = getString(R.string.depression_beck_test)
            text.text = getString(R.string.depression_beck_test_desc)
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        setFragmentResultListeners()

        return binding.root
    }

    private fun render(it: BeckDepressionScreenState) {
        when (it) {
            is BeckDepressionScreenState.Question -> {
                FragmentTestQuestion().apply {
                    arguments = bundleOf(
                        FragmentTestQuestion.ANSWER_VARIANTS to it.answerVariants,
                        FragmentTestQuestion.IS_FIRST to (it.number == 0)
                    )
                    isCancelable = false
                }.show(childFragmentManager, TAG)
            }
            is BeckDepressionScreenState.Result -> {
                Toast.makeText(requireContext(), it.score.toString(), Toast.LENGTH_LONG).show()
            }
        }
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
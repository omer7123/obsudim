package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.databinding.FragmentPassingTestBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.diagnostics.PassingTestScreenState
import com.example.mypsychologist.presentation.diagnostics.PassingTestViewModel
import javax.inject.Inject


class PassingTestFragment : Fragment() {
    private var _binding: FragmentPassingTestBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var vmFactory: MultiViewModelFactory
    private val viewModel: PassingTestViewModel by lazy {
        ViewModelProvider(this, vmFactory)[PassingTestViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().diagnosticComponent().create().inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPassingTestBinding.inflate(layoutInflater)
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        setFragmentResultListener()
        return binding.root
    }

    private fun render(state: PassingTestScreenState) {
        when (state) {
            is PassingTestScreenState.Error -> {
                requireContext().showToast(state.msg)
                Log.e("FDFD", state.msg)
            }

            PassingTestScreenState.Initial -> {}
            PassingTestScreenState.Loading -> {}
            is PassingTestScreenState.Question -> {
                FragmentTestQuestion.newInstance(
                    state.answerVariants,
                    state.number,
                    state.count
                ).show(childFragmentManager, PassingTestFragment.TAG)
            }

            is PassingTestScreenState.Result -> {
                Log.e("RESULT OF TEST", state.result.toString())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        binding.title.text = requireArguments().getString(TITLE)
        binding.text.text = requireArguments().getString(DESCRIPTION)
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.getQuestions(requireArguments().getString(TEST_ID).toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setFragmentResultListener() {
        childFragmentManager.setFragmentResultListener(
            FragmentTestQuestion.ANSWER,
            viewLifecycleOwner
        ) { _, bundle ->
            viewModel.saveAnswerAndGoToNext(
                bundle.getInt(FragmentTestQuestion.SCORE),
                requireArguments().getString(TEST_ID)
            )
        }

        childFragmentManager.setFragmentResultListener(
            FragmentTestQuestion.GO_BACK,
            viewLifecycleOwner
        ) { _, _ ->
            viewModel.previousQuestion()
        }
    }

    companion object {
        const val TEST_ID = "test_id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        private const val TAG = "tag"

        @JvmStatic
        fun newInstance(testId: String, title: String, description: String) =
            PassingTestFragment().apply {
                arguments = bundleOf(
                    TEST_ID to testId,
                    TITLE to title,
                    DESCRIPTION to description,
                )
            }
    }
}
package com.obsudim.mypsychologist.ui.diagnostics.passingTestFragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.databinding.FragmentPassingTestBinding
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.QuestionOfTestEntity
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.ResultAfterSaveEntity
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.extensions.showToast
import com.obsudim.mypsychologist.presentation.di.MultiViewModelFactory
import com.obsudim.mypsychologist.presentation.diagnostics.passingTestFragment.PassingTestScreenState
import com.obsudim.mypsychologist.presentation.diagnostics.passingTestFragment.PassingTestViewModel
import com.obsudim.mypsychologist.presentation.diagnostics.testResultFragment.TestResultViewModel
import com.obsudim.mypsychologist.ui.diagnostics.testResultFragment.TestResultFragment
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.previousQuestion()
            }
        })
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
        return binding.root
    }

    private lateinit var testTitle: String

    @RequiresApi(Build.VERSION_CODES.O)
    private fun render(state: PassingTestScreenState) {
        when (state) {
            is PassingTestScreenState.Content -> {
                testTitle = state.title
            }

            PassingTestScreenState.Initial -> {}
            PassingTestScreenState.Loading -> {}
            is PassingTestScreenState.Questions -> {
                setupViewPager(state.list, state.list.size)
            }

            is PassingTestScreenState.Question -> {
                binding.testQuestionVp.currentItem = state.position
            }

            is PassingTestScreenState.Result -> {
                showResult(state.result)
            }

            is PassingTestScreenState.Error -> {
                requireContext().showToast(state.msg)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupViewPager(
        questions: List<QuestionOfTestEntity>,
        totalNumber: Int
    ) {
        binding.testQuestionVp.adapter =
            TestQuestionAdapter(
                questions, totalNumber,
                onAnswerClick = { score ->
                    viewModel.saveAnswerAndGoToNext(
                        score = score,
                        testId = requireArguments().getString(TEST_ID),
                        taskId = requireArguments().getString(TASK_ID) ?: ""
                    )
                }, onBackClick = {
                    findNavController().popBackStack()
//                    viewModel.previousQuestion()
                })

        binding.testQuestionVp.isUserInputEnabled = false
    }

    private fun showResult(conclusions: ResultAfterSaveEntity) {
       findNavController()
            .navigate(
            R.id.action_passingTestFragment_to_result_test_graph, bundleOf(
                TestResultFragment.TEST_TITLE to testTitle,
                TestResultViewModel.TEST_RESULT_ID to conclusions.testResultId
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        viewModel.getQuestions(requireArguments().getString(TEST_ID).toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setFragmentResultListener() {
        childFragmentManager.setFragmentResultListener(
            FragmentTestQuestion.ANSWER,
            viewLifecycleOwner
        ) { _, bundle ->
            viewModel.saveAnswerAndGoToNext(
                score = bundle.getInt(FragmentTestQuestion.SCORE),
                testId = requireArguments().getString(TEST_ID),
                taskId = requireArguments().getString(TASK_ID) ?: ""
            )
        }

        childFragmentManager.setFragmentResultListener(
            FragmentTestQuestion.GO_BACK,
            viewLifecycleOwner
        ) { _, _ ->
            //    viewModel.previousQuestion()
        }
    }

    companion object {
        const val TEST_ID = "test_id"
        const val TASK_ID = "task_id"
    }
}
package com.example.mypsychologist.ui.education

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.databinding.FragmentEducationBinding
import com.example.mypsychologist.domain.entity.educationEntity.EducationsEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.education.EducationScreenState
import com.example.mypsychologist.presentation.education.EducationViewModel
import com.example.mypsychologist.presentation.education.MarsAsCompleteStatus
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import javax.inject.Inject

class EducationFragment : Fragment() {

    private var binding: FragmentEducationBinding by autoCleared()

    @Inject
    lateinit var vmFactory: EducationViewModel.Factory
    private val viewModel: EducationViewModel by viewModels { vmFactory }

    private lateinit var mainAdapter: MainAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().educationComponent().create().inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEducationBinding.inflate(inflater, container, false)

        setupAdapter()

        binding.includeToolbar.toolbar.apply {
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        viewModel.marsAsCompleteStatus.observe(viewLifecycleOwner) { status ->
            renderStatus(status)
        }

        return binding.root
    }

    private fun renderStatus(status: MarsAsCompleteStatus) {
        when (status) {
            is MarsAsCompleteStatus.Error -> requireContext().showToast(status.msg)
            MarsAsCompleteStatus.Success -> findNavController().popBackStack()
        }
    }

    private fun render(state: EducationScreenState) {
        when (state) {
            is EducationScreenState.Content -> setupContent(state.data)
            is EducationScreenState.Error -> {
                requireContext().showToast(state.msg)
            }

            EducationScreenState.Initial -> Unit
            EducationScreenState.Loading -> Unit
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMaterial(requireArguments().getString(TOPIC_TAG).toString())
    }


    private fun setupContent(data: EducationsEntity) {
        binding.saveButton.isVisible = true
        binding.saveButton.setOnClickListener {
//            viewModel.saveProgress(data.materials.last().id)
            markTaskAsCompleted()
        }

        binding.title.text = data.theme
        mainAdapter.submitList(data.materials.toDelegateItems())

        binding.cardsRw.scrollToPosition(
            data.score
        )
    }

    private fun markTaskAsCompleted() {
        requireArguments().getString(TASK_ID)?.let { taskId ->
            viewModel.markAsCompleteTask(taskId)
        }
    }

    private fun setupAdapter() {
        mainAdapter = MainAdapter().apply {
            addDelegate(
                EducationCardDelegate()
            )
        }
        binding.cardsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
            setHasFixedSize(true)
        }
    }

    companion object {
        const val TOPIC_TAG = "tag"
        const val TASK_ID = "task_id"
    }
}
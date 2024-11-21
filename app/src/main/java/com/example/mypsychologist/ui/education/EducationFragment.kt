package com.example.mypsychologist.ui.education

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.databinding.FragmentEducationBinding
import com.example.mypsychologist.domain.entity.educationEntity.EducationsEntity
import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.education.EducationScreenState
import com.example.mypsychologist.presentation.education.EducationViewModel
import com.example.mypsychologist.ui.PagerAdapter
import com.example.mypsychologist.ui.autoCleared
import javax.inject.Inject

class EducationFragment : Fragment() {

    private var binding: FragmentEducationBinding by autoCleared()

    @Inject
    lateinit var vmFactory: EducationViewModel.Factory
    private val viewModel: EducationViewModel by viewModels { vmFactory }

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

        binding.includeToolbar.toolbar.apply {
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        return binding.root
    }

    private fun render(state: EducationScreenState) {
        when (state) {
            is EducationScreenState.Content -> setupContent(state.data)
            is EducationScreenState.Error -> {requireContext().showToast(state.msg)}
            EducationScreenState.Initial -> {}
            EducationScreenState.Loading -> {}
            EducationScreenState.Success -> Unit
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMaterial(requireArguments().getString(TOPIC_TAG).toString())
    }

    private fun setupContent(data: EducationsEntity) {
        binding.includeToolbar.toolbar.title = data.theme

        val pagerAdapter = PagerAdapter(childFragmentManager, lifecycle)
        binding.educationVp.adapter = pagerAdapter

        pagerAdapter.update(
            generateFragmentList(data.materials)
        )

        binding.educationVp.setCurrentItem(
            data.score, false
        )
    }

    private fun generateFragmentList(items: List<ItemMaterialEntity>): List<Fragment> {

        return items.mapIndexed { index, item ->
            EducationItemFragment.newInstance(
                index,
                items.size,
                item.text,
                item.id,
                taskId = requireArguments().getString(TASK_ID) ?: ""
            )
        }
    }

    companion object {
        const val TOPIC_TAG = "tag"
        const val TASK_ID = "task_id"
    }
}
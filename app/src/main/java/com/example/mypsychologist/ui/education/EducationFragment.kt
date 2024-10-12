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
import com.example.mypsychologist.domain.entity.educationEntity.ThemeEntity
import com.example.mypsychologist.domain.useCase.GetEducationMaterialUseCase
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.serializable
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
            title = requireArguments().serializable<ThemeEntity>(TOPIC_TAG)?.theme

            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        setupViewPager()

        return binding.root
    }

    private fun setupViewPager() {
        val pagerAdapter = PagerAdapter(childFragmentManager, lifecycle)
        binding.educationVp.adapter = pagerAdapter

        pagerAdapter.update(
            generateFragmentList(
                resources.getStringArray(
                    viewModel.getMaterial(
                        requireArguments().serializable(TOPIC_TAG)!!
                    )
                )
            )
        )
//        binding.educationVp.setCurrentItem(requireArguments().getInt(CURRENT), false)
    }

    private fun generateFragmentList(items: Array<String>): List<Fragment> {
        val topicTag: GetEducationMaterialUseCase.Topic =
            requireArguments().serializable(TOPIC_TAG)!!

        return items.mapIndexed { index, text ->
            EducationItemFragment.newInstance(index, items.size, text, topicTag)
        }
    }

    companion object {
        const val TOPIC_TAG = "tag"
    }
}
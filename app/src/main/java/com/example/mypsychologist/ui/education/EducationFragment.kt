package com.example.mypsychologist.ui.education

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.databinding.FragmentEducationBinding
import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.domain.entity.educationEntity.ThemeEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.serializable
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
            title = requireArguments().serializable<ThemeEntity>(TOPIC_TAG)?.theme

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
            is EducationScreenState.Content -> setupViewPager(state.data)
            is EducationScreenState.Error -> {}
            EducationScreenState.Initial -> {}
            EducationScreenState.Loading -> {}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topic = requireArguments().serializable<ThemeEntity>(key = TOPIC_TAG)
        viewModel.getMaterial(topic!!.id)
    }

    private fun setupViewPager(data: List<ItemMaterialEntity>) {
        val pagerAdapter = PagerAdapter(childFragmentManager, lifecycle)
        binding.educationVp.adapter = pagerAdapter

        pagerAdapter.update(
            generateFragmentList(data)
        )
        Log.e("item", requireArguments().serializable<ThemeEntity>(TOPIC_TAG).toString())
        binding.educationVp.setCurrentItem(
            requireArguments().serializable<ThemeEntity>(TOPIC_TAG)!!.score, false
        )
    }

    private fun generateFragmentList(items: List<ItemMaterialEntity>): List<Fragment> {

        return items.mapIndexed { index, item ->
            EducationItemFragment.newInstance(
                index,
                items.size,
                item.text,
                item.id
            )
        }
    }

    companion object {
        const val TOPIC_TAG = "tag"
    }
}
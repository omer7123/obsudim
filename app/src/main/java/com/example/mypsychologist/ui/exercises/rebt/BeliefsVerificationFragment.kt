package com.example.mypsychologist.ui.exercises.rebt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentBeliefsVerificationBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.presentation.exercises.BeliefsScreenState
import com.example.mypsychologist.presentation.exercises.BeliefsVerificationViewModel
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.ui.PagerAdapter
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class BeliefsVerificationFragment : Fragment() {
    private var binding: FragmentBeliefsVerificationBinding by autoCleared()

    @Inject
    lateinit var vmFactory: BeliefsVerificationViewModel.Factory
    private val viewModel: BeliefsVerificationViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeliefsVerificationBinding.inflate(inflater, container, false)

        binding.toolbar.toolbar.apply {
            title = getString(R.string.beliefs_check)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)


        return binding.root
    }

    private fun render(state: BeliefsScreenState) {
        when (state) {
            is BeliefsScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    requireContext().showToast(getString(R.string.network_error))
            }
            is BeliefsScreenState.Data -> {
                binding.progressBar.isVisible = false
                setupViewPager(generateFragmentList(state.beliefs))
            }
            is BeliefsScreenState.Error -> {
                binding.progressBar.isVisible = false
                requireContext().showToast(getString(R.string.db_error))
            }
            is BeliefsScreenState.Init -> Unit
        }
    }

    private var currentItem = 0

    private fun setupViewPager(fragments: List<Fragment>) {
        val pagerAdapter = PagerAdapter(childFragmentManager, lifecycle)

        binding.beliefsViewPager.isUserInputEnabled = false

        childFragmentManager.setFragmentResultListener(
            BeliefVerificationFragment.FINISH,
            viewLifecycleOwner
        ) { _, _ ->
            currentItem++
            if (currentItem < fragments.size)
                binding.beliefsViewPager.setCurrentItem(currentItem, true)
            else
                findNavController().popBackStack()
        }

        binding.beliefsViewPager.adapter = pagerAdapter

        pagerAdapter.update(fragments)
    }

    private fun generateFragmentList(beliefs: Map<String, String>) =
        beliefs.map { (type, belief) ->
            BeliefVerificationFragment.newInstance(type, belief)
        }.asReversed()
}
package com.example.mypsychologist.ui.exercises.cbt

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentFreeDiaryBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.exercises.FreeDiariesViewModel
import com.example.mypsychologist.presentation.exercises.ThoughtDiariesScreenState
import com.example.mypsychologist.presentation.exercises.ThoughtDiariesViewModel
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.psychologist.ExercisesFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class FreeDiaryFragment : Fragment() {
    private var binding: FragmentFreeDiaryBinding by autoCleared()
    private lateinit var adapter: MainAdapter


    private lateinit var clientId: String

//    @Inject
//    lateinit var vmFactory: FreeDiariesViewModel.Factory
//
//    private val viewModel: FreeDiariesViewModel by viewModels {
//        FreeDiariesViewModel.provideFactory(
//            vmFactory,
//            clientId
//        )
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        clientId = arguments?.getString(ExercisesFragment.CLIENT_ID, ThoughtDiariesViewModel.OWN)
//            ?: ThoughtDiariesViewModel.OWN
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFreeDiaryBinding.inflate(layoutInflater)

        setupToolbarAndFAB()

        setupAdapter()

//        viewModel.screenState
//            .flowWithLifecycle(lifecycle)
//            .onEach { render(it) }
//            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun setupToolbarAndFAB() {
        binding.include.toolbar.apply {
            title = getString(R.string.free_diary)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }

//        if (clientId != ThoughtDiariesViewModel.OWN)
//            binding.newDiaryFab.isVisible = false
//        else
//            binding.newDiaryFab.setOnClickListener {
//                findNavController().navigate(R.id.newFreeDiaryFragment)
//            }
    }

    private fun setupAdapter() {
        adapter = MainAdapter().apply {
            addDelegate(
                RecordDelegate { id ->
               /*     findNavController().navigate(
                        R.id.fragment_diary,
                        bundleOf(
                            FragmentThoughtDiary.ID to id,
                            ExercisesFragment.CLIENT_ID to clientId
                        )
                    ) */
                }
            )

            binding.recordsRw.adapter = this
            binding.recordsRw.layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun render(it: ThoughtDiariesScreenState) {
        when (it) {
            is ThoughtDiariesScreenState.Data -> {
                binding.progressBar.isVisible = false
                binding.includePlaceholder.layout.isVisible = false

                if (it.records.isNotEmpty())
                    adapter.submitList(it.records.toDelegateItems())
                else {
                    showPlaceholderForEmptyList()
                }
            }
            is ThoughtDiariesScreenState.Init -> {}
            is ThoughtDiariesScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    binding.includePlaceholder.layout.isVisible = true
            }
            is ThoughtDiariesScreenState.Error -> {
                binding.progressBar.isVisible = false
                requireContext().showToast(getString(R.string.network_error))
            }
        }
    }

    private fun showPlaceholderForEmptyList() {
        binding.includePlaceholder.apply {
            image.setImageResource(R.drawable.ic_import_contacts)
            title.text = getString(R.string.nothing)
            text.text = getString(R.string.no_diaries)
            layout.isVisible = true
        }
    }

    override fun onResume() {
        super.onResume()
//        viewModel.loadDiaries()
    }
}
package com.example.mypsychologist.ui.exercises.cbt

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentFreeDiaryBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.authentication.authFragment.AuthViewModel
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.exercises.FreeDiariesViewModel
import com.example.mypsychologist.presentation.exercises.ThoughtDiariesScreenState
import com.example.mypsychologist.presentation.exercises.ThoughtDiariesViewModel
import com.example.mypsychologist.presentation.main.FeedbackViewModel
import com.example.mypsychologist.presentation.main.mainFragment.MainViewModel
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.psychologist.ExercisesFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class FreeDiaryFragment : Fragment() {
    private var binding: FragmentFreeDiaryBinding by autoCleared()
    private lateinit var adapter: MainAdapter

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: FreeDiariesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FreeDiariesViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFreeDiaryBinding.inflate(layoutInflater)
        setupToolbarAndFAB()
        setupAdapter()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadDiaries()
    }

    private fun setupToolbarAndFAB() {
        binding.include.toolbar.apply {
            title = getString(R.string.free_diary)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }

        binding.newDiaryFab.setOnClickListener {
            findNavController().navigate(R.id.newFreeDiaryFragment)
        }
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

                Log.e("Diaries", it.records.toString())
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
}
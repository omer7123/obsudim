package com.obsudim.mypsychologist.ui.psychologist.psychologistWithTasksFragment

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.databinding.FragmentOwnPsychologistsBinding
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.extensions.isNetworkConnect
import com.obsudim.mypsychologist.extensions.showToast
import com.obsudim.mypsychologist.presentation.psychologist.psychologistWithTaskFragment.PsychologistWithTaskScreenState
import com.obsudim.mypsychologist.presentation.psychologist.psychologistWithTaskFragment.PsychologistsWithTasksViewModel
import com.obsudim.mypsychologist.ui.core.adapter.MainAdapter
import com.obsudim.mypsychologist.ui.core.autoCleared
import com.obsudim.mypsychologist.ui.core.delegateItems.DelegateItem
import com.obsudim.mypsychologist.ui.diagnostics.testsFragment.DiagnosticDialogFragment
import com.obsudim.mypsychologist.ui.psychologist.requestToPsychologistFragment.RequestToPsychologistFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PsychologistsWithTasksFragment : Fragment() {

    private var binding: FragmentOwnPsychologistsBinding by autoCleared()

    @Inject
    lateinit var vmFactory: PsychologistsWithTasksViewModel.Factory
    private val viewModel: PsychologistsWithTasksViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().psychologistComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOwnPsychologistsBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.psychologists)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.findPsychologistButton.setOnClickListener {
            viewModel.getPsychologists()
//            findNavController().navigate(R.id.fragment_psychologists)

        }

//        binding.toAllButton.setOnClickListener {
//            findNavController().navigate(R.id.fragment_psychologists)
//        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        viewModel.initial()
        return binding.root
    }

    private fun render(state: PsychologistWithTaskScreenState) {
        when (state) {
            is PsychologistWithTaskScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    requireContext().showToast(getString(R.string.network_error))
            }

            is PsychologistWithTaskScreenState.Content -> {
                binding.progressBar.isVisible = false
                binding.placeholder.isVisible = false
                setupAdapter(state.items)
            }

            is PsychologistWithTaskScreenState.Error -> {
                requireContext().showToast(state.msg)
            }

            is PsychologistWithTaskScreenState.Init -> Unit

            PsychologistWithTaskScreenState.PlaceHolderTaskState -> {
                binding.progressBar.isVisible = false
                binding.placeholder.isVisible = true
                binding.findPsychologistButton.isVisible = false
                binding.text.text = getString(R.string.tasks_not_require)
            }

            PsychologistWithTaskScreenState.PlaceHolderPsychologistsState -> {
                binding.progressBar.isVisible = false
                binding.placeholder.isVisible = true
                binding.title.text = getString(R.string.specialist_not_found)
                binding.text.text =
                    getString(R.string.you_have_not_manager)
            }
        }
    }

    private fun setupAdapter(items: List<DelegateItem>) {
        binding.psychologistsRw.apply {
            layoutManager = LinearLayoutManager(context)


            adapter = MainAdapter().apply {
                addDelegate(OwnPsychologistDelegate { psychologistId ->
                    val bundle = Bundle()
                    bundle.putString(RequestToPsychologistFragment.PSYCHOLOGIST_ID, psychologistId)
                    findNavController().navigate(R.id.fragment_request_to_psychologist, bundle)
                })
                addDelegate(TaskDelegate(check = { taskId, isChecked ->
                    viewModel.markTask(taskId, isChecked)
                }, onItemClickListener = { testId, testTitle, desc ->
                    DiagnosticDialogFragment.newInstance(testId, testTitle, desc)
                        .show(childFragmentManager, DiagnosticDialogFragment.TAG)
                }
                ))

                submitList(items)
            }
        }
    }
}
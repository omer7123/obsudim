package com.example.mypsychologist.ui.exercises.cbt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.*
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.databinding.FragmentNewThoughtDiaryBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.exercises.NewThoughtDiaryScreenState
import com.example.mypsychologist.presentation.exercises.NewThoughtDiaryViewModel
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentNewCBTDiary : Fragment() {

    private var binding: FragmentNewThoughtDiaryBinding by autoCleared()
    private var navbarHider: NavbarHider? = null

    @Inject
    lateinit var vmFactory: NewThoughtDiaryViewModel.Factory
    private val viewModel: NewThoughtDiaryViewModel by viewModels { vmFactory }

    private lateinit var mainAdapter: MainAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)

        if (context is NavbarHider) {
            navbarHider = context
            navbarHider!!.setNavbarVisibility(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewThoughtDiaryBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.title = getString(R.string.cbt_diary)

        setupAdapter(viewModel.items)

        setupListeners()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun setupAdapter(items: List<DelegateItem>) {
        mainAdapter = MainAdapter().apply {
            addDelegate(
                InputDelegate(::showHint)
            )
            addDelegate(
                SeekBarDelegate(viewModel::setLevel)
            )

            submitList(items)
        }

        binding.itemsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }
    }

    private fun setupListeners() {
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.saveButton.setOnClickListener { viewModel.tryToSaveDiary() }
    }

    private fun showHint(titleId: Int, hintId: Int) {
        FragmentHint.newInstance(titleId, hintId)
            .show(childFragmentManager, "tag")
    }

    private fun render(state: NewThoughtDiaryScreenState) {
        when (state) {
            is NewThoughtDiaryScreenState.RequestResult -> {
                renderRequest(state.resource)
            }
            is NewThoughtDiaryScreenState.ValidationError -> {
                mainAdapter.submitList(state.listWithErrors)
            }
            is NewThoughtDiaryScreenState.Init -> {}
        }
    }

    private fun renderRequest(resource: Resource<String>) {

        when(resource) {
            is Resource.Error -> {
                requireContext().showToast(resource.msg.toString())
            }
            is Resource.Success -> {
                findNavController().popBackStack()
                requireContext().showToast(getString(R.string.success))
            }
            is Resource.Loading -> Unit
        }
    }

    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }
}
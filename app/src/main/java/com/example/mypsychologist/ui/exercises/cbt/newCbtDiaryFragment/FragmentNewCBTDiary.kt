package com.example.mypsychologist.ui.exercises.cbt.newCbtDiaryFragment

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
import com.example.mypsychologist.NavbarHider
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentNewThoughtDiaryBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.exercises.NewThoughtDiaryViewModel
import com.example.mypsychologist.presentation.exercises.exercisesFragment.SaveExerciseStatus
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.cbt.HintDelegateItem
import com.example.mypsychologist.ui.exercises.cbt.InputExerciseDelegateItem
import com.example.mypsychologist.ui.exercises.cbt.SeekBarDelegate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentNewCBTDiary : Fragment() {

    private var binding: FragmentNewThoughtDiaryBinding by autoCleared()
    private var navbarHider: NavbarHider? = null

    private lateinit var dataList: List<DelegateItem>

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
        setupListeners()

   /*     viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope) */

        viewModel.saveStatus
            .flowWithLifecycle(lifecycle)
            .onEach { renderSaveStatus(it) }
            .launchIn(lifecycleScope)

        dataList = viewModel.items
        setupAdapter(dataList)

        /*    if (savedInstanceState == null)
                viewModel.getFields(requireArguments().getString(EXERCISE_ID).toString()) */

        return binding.root
    }

    private fun renderSaveStatus(status: SaveExerciseStatus) {
        when(status){
            is SaveExerciseStatus.Error -> {
                requireContext().showToast(status.msg)
            }
            SaveExerciseStatus.Init -> Unit
            SaveExerciseStatus.Loading -> {
                binding.progressCircular.isVisible = true
            }
            SaveExerciseStatus.Success -> findNavController().popBackStack()
        }
    }

 /*   private fun render(state: NewExerciseScreenState) {
        when (state) {
            is NewExerciseScreenState.Init -> Unit
            is NewExerciseScreenState.Content -> renderContent(state.data)
            is NewExerciseScreenState.Error -> {
                binding.progressCircular.isVisible = false
                requireContext().showToast(state.msg)
            }
            NewExerciseScreenState.Loading -> {
                binding.progressCircular.isVisible = true
            }
        }
    }

    private fun renderContent(data: ExerciseDetailWithDelegateItem) {
        binding.progressCircular.isVisible = false
        binding.includeToolbar.toolbar.title = data.title

        dataList = data.fields
        mainAdapter = MainAdapter().apply {
            addDelegate(
                InputExerciseDelegate(dataList.size)
            )
            addDelegate(
                SliderDelegate()
            )
            addDelegate(
                HintDelegate()
            )

            submitList(dataList)
        }

        binding.itemsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }
    } */

    private fun setupAdapter(items: List<DelegateItem>) {
        dataList = viewModel.items
        mainAdapter = MainAdapter().apply {
            addDelegate(
                ThoughtDiaryDelegate()
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

        binding.hintsChip.setOnClickListener {
            showHints()
        }

        binding.saveButton.setOnClickListener {
            viewModel.tryToSaveDiary(
            //    requireArguments().getString(EXERCISE_ID).toString(),
                requireArguments().getString(TASK_ID) ?: ""
            )
        }
    }

    private fun showHints() {
        val items = dataList.map { it }.toMutableList()
        dataList.forEach {
            if (dataList == mainAdapter.currentList) {
                if (it is InputExerciseDelegateItem) {
                    val position = items.indexOf(it)
                    items.add(position + 1, HintDelegateItem(it.content().helperId!!))
                }
                mainAdapter.submitList(items)
            }else{
                mainAdapter.submitList(dataList)
            }

        }
    }

    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }

    companion object {
        const val EXERCISE_ID = "ID_EXERCISE"
        const val TASK_ID = "TASK_ID"
    }
}
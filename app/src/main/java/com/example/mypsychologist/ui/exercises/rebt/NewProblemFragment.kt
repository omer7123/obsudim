package com.example.mypsychologist.ui.exercises.rebt

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.AddProblemBottomSheetBinding
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.getAppComponent
import com.example.mypsychologist.isNetworkConnect
import com.example.mypsychologist.presentation.exercises.AddProblemViewModel
import com.example.mypsychologist.presentation.exercises.NewProblemScreenState
import com.example.mypsychologist.showToast
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NewProblemFragment : BottomSheetDialogFragment() {

    private var binding: AddProblemBottomSheetBinding by autoCleared()

    @Inject
    lateinit var vmFactory: AddProblemViewModel.Factory
    private val viewModel: AddProblemViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddProblemBottomSheetBinding.inflate(inflater, container, false)

        setupChips()

        setupListeners()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun setupChips() {
        resources.getStringArray(R.array.harmful_emotions).forEach { title ->

            val chip = layoutInflater.inflate(
                R.layout.filter_chip,
                binding.moods,
                false
            ) as Chip

            binding.moods.addView(
                chip.apply {
                    text = title
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked)
                            viewModel.moods.add(title)
                        else
                            viewModel.moods.remove(title)
                    }
                }
            )
        }
    }

    private fun setupListeners() {
        binding.saveButton.setOnClickListener {
            viewModel.tryToSave(binding.problemField.text.toString())
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun render(state: NewProblemScreenState) {
        when (state) {
            is NewProblemScreenState.Success -> {
                showToast(getString(R.string.success))

                setFragmentResult(
                    NEW_PROBLEM, bundleOf(
                        ID to state.id,
                        TITLE to binding.problemField.text.toString()
                    )
                )

                dismiss()
            }

            is NewProblemScreenState.Error -> {
                showToast(
                    getString(
                        if (isNetworkConnect())
                            R.string.db_error
                        else
                            R.string.network_error
                    )
                )
            }

            is NewProblemScreenState.ValidationError -> {
                if (!state.textIsCorrect)
                    binding.problemLayout.error = getString(R.string.necessary_to_fill)
                if (!state.chipsAreCorrect)
                    showToast(getString(R.string.necessary_to_choice))
            }

            is NewProblemScreenState.Init -> Unit
        }
    }

    companion object {
        const val NEW_PROBLEM = "new problem"
        const val ID = "id"
        const val TITLE = "title"
    }
}
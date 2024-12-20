package com.example.mypsychologist.ui.main

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.*
import com.example.mypsychologist.databinding.FragmentPsychologistQuestionnaireBinding
import com.example.mypsychologist.domain.entity.PsychologistInfo
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.extensions.toDateString
import com.example.mypsychologist.presentation.main.PsychologistFormScreenState
import com.example.mypsychologist.presentation.main.PsychologistQuestionnaireViewModel
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.FragmentEditField
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

class PsychologistQuestionnaireFragment : Fragment() {

    private var binding: FragmentPsychologistQuestionnaireBinding by autoCleared()

    @Inject
    lateinit var vmFactory: PsychologistQuestionnaireViewModel.Factory
    private val viewModel: PsychologistQuestionnaireViewModel by viewModels { vmFactory }

    lateinit var adapter: MainAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPsychologistQuestionnaireBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.title = getString(R.string.psychologist_questionnaire)

        setupSpecializationChips()

        setupAdapter()

        setupListeners()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun setupSpecializationChips() {
        resources.getStringArray(R.array.specializations).forEach { specialization ->
            val chip = layoutInflater.inflate(
                R.layout.filter_chip,
                binding.specializationsGroup,
                false
            ) as Chip

            binding.specializationsGroup.addView(
                chip.apply {
                    text = specialization
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked)
                            viewModel.addSpecialization(specialization)
                        else
                            viewModel.removeSpecialization(specialization)
                    }
                }
            )
        }
    }

    private fun setupAdapter() {
        adapter = MainAdapter().apply {
            addDelegate(
                DeletableStringsDelegate { course ->
                    viewModel.removeCourse(course)
                    adapter.submitList(viewModel.info.courses.toDelegateItems())
                }
            )
            binding.coursesRw.adapter = this
            binding.coursesRw.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupListeners() {
        binding.apply {
            includeToolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            acceptButton.setOnClickListener {
                acceptanceTooltip.isVisible = false
            }
            toTextButton.setOnClickListener {
                findNavController().navigate(R.id.fragment_long_text, bundleOf(
                    LongTextFragment.TITLE to getString(R.string.approval),
                    LongTextFragment.TEXT_ID to R.string.approval_text
                ))
            }

            nameField.addTextChangedListener {
                viewModel.setName(it.toString())
                nameLayout.error = null
            }
            educationField.addTextChangedListener {
                viewModel.setEducation(it.toString())
                educationLayout.error = null
            }
            aboutField.addTextChangedListener {
                viewModel.setAbout(it.toString())
                educationLayout.error = null
            }
            cityField.addTextChangedListener {
                viewModel.setCity(it.toString())
                cityLayout.error = null
            }
            formatField.addTextChangedListener {
                viewModel.setFormat(it.toString())
                formatLayout.error = null
            }

            genderRg.setOnCheckedChangeListener { _, id ->
                viewModel.setGender(requireActivity().findViewById<RadioButton>(id).text.toString())
            }

            editBirthdayButton.setOnClickListener {
                val calendar = Calendar.getInstance()

                DatePickerDialog(
                    requireContext(),
                    R.style.Theme_MyPsychologist,
                    { _, year, month, day ->

                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, day)

                        birthdayField.text = Date(calendar.timeInMillis).toDateString()
                        viewModel.setBirthday(calendar.timeInMillis)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            addCourseButton.setOnClickListener {

                childFragmentManager.setFragmentResultListener(
                    ADD_COURSE, viewLifecycleOwner
                ) { _, bundle ->
                    viewModel.addCourse(bundle.getString(FragmentEditField.NEW_TEXT)!!)
                    adapter.submitList(viewModel.info.courses.toDelegateItems())
                }

                FragmentEditField.newInstance(getString(R.string.professional_development), "")
                    .show(childFragmentManager, ADD_COURSE)
            }

            saveButton.setOnClickListener {
                viewModel.tryToSave()
            }
        }
    }

    private fun render(state: PsychologistFormScreenState) {
        when (state) {
            is PsychologistFormScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.progressBar.isVisible = true
                else
                    requireContext().showToast(getString(R.string.network_error))
            }
            is PsychologistFormScreenState.RequestResult -> {
                binding.progressBar.isVisible = false
                if (state.success) {
                    requireContext().showToast(getString(R.string.success))
                    writeInPreferences()
                    findNavController().popBackStack()

                } else
                    requireContext().showToast(getString(R.string.db_error))
            }
            is PsychologistFormScreenState.ValidationError -> {
                val fieldAndViews = mapOf(
                    PsychologistInfo::name.name to binding.nameLayout,
                    PsychologistInfo::education.name to binding.educationLayout,
                    PsychologistInfo::about.name to binding.aboutLayout,
                    PsychologistInfo::city.name to binding.cityLayout,
                    PsychologistInfo::formats.name to binding.formatLayout
                )

                state.fields.forEach {
                    fieldAndViews[it]?.error = getString(R.string.necessary_to_fill)
                }
            }
            is PsychologistFormScreenState.Init -> Unit
        }
    }

    private fun writeInPreferences() {
        with(requireActivity().getPreferences(Context.MODE_PRIVATE).edit()) {
            putBoolean(getString(R.string.psychologist_is_checked), true)
            putBoolean(getString(R.string.is_psychologist), true)
            apply()
        }
    }

    companion object {
        private const val ADD_COURSE = "add course"
    }
}
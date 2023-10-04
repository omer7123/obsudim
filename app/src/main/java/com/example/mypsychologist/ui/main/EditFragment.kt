package com.example.mypsychologist.ui.main

import android.app.DatePickerDialog
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
import com.example.mypsychologist.*
import com.example.mypsychologist.databinding.FragmentEditBinding
import com.example.mypsychologist.domain.entity.ClientDataEntity
import com.example.mypsychologist.presentation.main.EditScreenState
import com.example.mypsychologist.presentation.main.EditViewModel
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.exercises.FragmentEditField
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

class EditFragment : Fragment() {

    private var binding: FragmentEditBinding by autoCleared()

    @Inject
    lateinit var vmFactory: EditViewModel.Factory
    private val viewModel: EditViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.edit_data)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        setupTitlesAndIcons()

        setupListeners()

        return binding.root
    }

    private fun render(state: EditScreenState) {
        when (state) {
            is EditScreenState.CurrentData -> {
                setupData(state.data)
            }
            is EditScreenState.Loading -> {
                if (!isNetworkConnect())
                    showToast(getString(R.string.network_error))
            }
            is EditScreenState.Response -> {
                if (state.result)
                    showToast(getString(R.string.success))
                else
                    showToast(getString(R.string.db_error))
            }
            is EditScreenState.Init -> Unit
        }
    }

    private fun setupData(info: ClientDataEntity) {
        binding.apply {
            nameCard.cardDescription.text = info.name
            birthdayCard.cardDescription.text = Date(info.birthday).toDateString()
            genderCard.cardDescription.text = info.gender
            diagnosisCard.cardDescription.text = info.diagnosis
            setupChips(info.request)
            mailCard.cardDescription.text = info.mail
            phoneCard.cardDescription.text = info.phone
            passwordCard.cardDescription.text = info.password
        }
    }

    private fun setupTitlesAndIcons() {
        binding.apply {
            nameCard.apply {
                cardTitle.text = getString(R.string.name)
                cardImage.setImageResource(R.drawable.ic_edit)
            }
            birthdayCard.apply {
                cardTitle.text = getString(R.string.birthday)
                cardImage.setImageResource(R.drawable.ic_edit)
            }
            genderCard.apply {
                cardTitle.text = getString(R.string.gender)
                cardImage.setImageResource(R.drawable.ic_edit)
            }
            diagnosisCard.apply {
                cardTitle.text = getString(R.string.diagnosis)
                cardImage.setImageResource(R.drawable.ic_edit)
            }
            mailCard.apply {
                cardTitle.text = getString(R.string.mail)
                cardImage.setImageResource(R.drawable.ic_edit)
            }
            phoneCard.apply {
                cardTitle.text = getString(R.string.phone)
                cardImage.setImageResource(R.drawable.ic_edit)
            }
            passwordCard.apply {
                cardTitle.text = getString(R.string.password)
                cardImage.setImageResource(R.drawable.ic_edit)
            }
        }
    }

    private fun setupListeners() {
        binding.apply {

            nameCard.apply {
                cardImage.setOnClickListener {

                    childFragmentManager.setFragmentResultListener(
                        EDIT_NAME, viewLifecycleOwner
                    ) { _, bundle ->

                        bundle.getString(FragmentEditField.NEW_TEXT)?.let { newText ->
                            viewModel.changeName(newText)
                            cardDescription.text = newText
                        }
                    }

                    FragmentEditField.newInstance(
                        cardTitle.text.toString(),
                        cardDescription.text.toString()
                    ).show(childFragmentManager, EDIT_NAME)
                }
            }

            birthdayCard.cardImage.setOnClickListener {
                val calendar = Calendar.getInstance()

                DatePickerDialog(
                    requireContext(),
                    R.style.Theme_MyPsychologist,
                    { _, year, month, day ->

                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, day)

                        birthdayCard.cardDescription.text =
                            Date(calendar.timeInMillis).toDateString()
                        viewModel.changeBirthday(calendar.timeInMillis)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            genderCard.apply {
                cardImage.setOnClickListener {

                    childFragmentManager.setFragmentResultListener(
                        EDIT_GENDER, viewLifecycleOwner
                    ) { _, bundle ->

                        bundle.getString(FragmentEditField.NEW_TEXT)?.let { newText ->
                            viewModel.changeGender(newText)
                            cardDescription.text = newText
                        }
                    }

                    FragmentEditField.newInstance(
                        cardTitle.text.toString(),
                        cardDescription.text.toString()
                    ).show(childFragmentManager, EDIT_GENDER)
                }
            }

            diagnosisCard.apply {
                cardImage.setOnClickListener {

                    childFragmentManager.setFragmentResultListener(
                        EDIT_DIAGNOSIS, viewLifecycleOwner
                    ) { _, bundle ->

                        bundle.getString(FragmentEditField.NEW_TEXT)?.let { newText ->
                            viewModel.changeDiagnosis(newText)
                            cardDescription.text = newText
                        }
                    }

                    FragmentEditField.newInstance(
                        cardTitle.text.toString(),
                        cardDescription.text.toString()
                    ).show(childFragmentManager, EDIT_DIAGNOSIS)
                }
            }

            changeRequestButton.setOnClickListener {

                childFragmentManager.setFragmentResultListener(
                    EDIT_REQUEST, viewLifecycleOwner
                ) { _, bundle ->

                    bundle.getStringArray(ChipsFragment.CHIPS)?.let { request ->
                        viewModel.changeRequest(request.toList())
                        setupChips(request.toList())
                    }
                }

                ChipsFragment.newInstance(
                    resources.getStringArray(R.array.specializations)
                ).show(childFragmentManager, EDIT_REQUEST)
            }
        }
    }

    private fun setupChips(list: List<String>) {
        binding.requestsGroup.removeAllViews()
        list.forEach {
            binding.requestsGroup.addView(
                Chip(requireContext()).apply {
                    text = it
                }
            )
        }
    }

    companion object {
        private const val EDIT_NAME = "edit name"
        private const val EDIT_GENDER = "edit gender"
        private const val EDIT_DIAGNOSIS = "edit diagnosis"
        private const val EDIT_REQUEST = "edit request"
    }
}
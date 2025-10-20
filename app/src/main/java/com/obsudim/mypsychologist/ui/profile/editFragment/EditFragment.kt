package com.obsudim.mypsychologist.ui.profile.editFragment

import android.app.DatePickerDialog
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
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.databinding.FragmentEditBinding
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.extensions.showToast
import com.obsudim.mypsychologist.presentation.profile.editFragment.EditScreenState
import com.obsudim.mypsychologist.presentation.profile.editFragment.EditViewModel
import com.obsudim.mypsychologist.ui.core.adapter.MainAdapter
import com.obsudim.mypsychologist.ui.core.autoCleared
import com.obsudim.mypsychologist.ui.core.delegateItems.DelegateItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


class EditFragment : Fragment() {

    private var binding: FragmentEditBinding by autoCleared()

    @Inject
    lateinit var vmFactory: EditViewModel.Factory
    private val viewModel: EditViewModel by viewModels { vmFactory }

    private lateinit var mainAdapter: MainAdapter

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

        setupListeners()
        setupAdapter(viewModel.items)

        return binding.root
    }


    private fun render(state: EditScreenState) {
        when (state) {
            is EditScreenState.CurrentData -> {
                binding.progressBar.isVisible = false
                binding.birthdayData.setText(state.birthday)
                mainAdapter.submitList(state.list)
            }

            is EditScreenState.Loading -> {
                binding.progressBar.isVisible = true
            }

            is EditScreenState.Response -> {
                binding.progressBar.isVisible = false
                render(state.result)
            }

            is EditScreenState.Error -> {
                binding.progressBar.isVisible = false
            }

            is EditScreenState.ValidationError -> {

            }

            is EditScreenState.Init -> Unit
        }
    }

    private fun render(resource: Resource<String>) {
        when (resource) {
            is Resource.Success -> {
                requireContext().showToast(resource.data)
                findNavController().popBackStack()
            }
            is Resource.Error -> {
                requireContext().showToast(resource.msg.toString())
            }
            is Resource.Loading -> { }
        }
    }


    private fun setupListeners() {
        binding.apply {

            birthday.setEndIconOnClickListener {
                setupDatePicker()
            }

            saveButton.setOnClickListener {
                viewModel.tryToSaveInfo(binding.nameEt.text.toString(), binding.birthdayData.text.toString())
            }
        }
    }
    private val DATE_PATTERN = "dd.MM.yyyy"
    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()

        // Обработчик выбора даты
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            // Установить дату в календарь
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            // Форматирование выбранной даты
            val selectedDate =
                SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(calendar.time)


            binding.birthday.editText?.setText(selectedDate)

        }
        DatePickerDialog(
            requireContext(),
            dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun isValidBirthday(calendar: Calendar): Boolean {
        val today = Calendar.getInstance()

        // Минимальный возраст (например, 18 лет)
        val minAge = 18
        val maxAge = 100

        val birthDate = calendar.clone() as Calendar
        birthDate.add(Calendar.YEAR, minAge)

        val oldestDate = calendar.clone() as Calendar
        oldestDate.add(Calendar.YEAR, -maxAge)

        // Возраст должен быть между minAge и maxAge
        return today.after(birthDate) && calendar.after(oldestDate)
    }

    private fun setupAdapter(items: List<DelegateItem>) {
        mainAdapter = MainAdapter().apply {
            addDelegate(
                InputDelegate()
            )

            submitList(items)
        }
//
//        binding.itemsRw.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = mainAdapter
//        }
    }

    /*private fun setupChips(list: List<TagEntity>) {
        binding.requestsGroup.removeAllViews()
        list.forEach {
            binding.requestsGroup.addView(
                Chip(requireContext()).apply {
                    text = it.text
                }
            )
        }
    }*/


    companion object {
        private var DATE_PATTERN = "yyyy-MM-dd"
        private const val EDIT_NAME = "edit name"
        private const val EDIT_GENDER = "edit gender"
        private const val EDIT_DIAGNOSIS = "edit diagnosis"
        private const val EDIT_REQUEST = "edit request"
        private const val SUCCESS = "Successfully"
    }
}
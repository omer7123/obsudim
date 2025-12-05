package com.obsudim.mypsychologist.ui.profile.editFragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.databinding.FragmentEditBinding
import com.obsudim.mypsychologist.databinding.IncludeEditTextBinding
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserDataEntity
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserInfoEntity
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
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        setupDefaultFields()

        setupListeners()

        return binding.root
    }

    private fun setupDefaultFields() {
        binding.apply {
            name.editText.apply {
                hint = getString(R.string.name)
                textChangeListener(viewModel::setName)
            }

            description.editText.apply {
                hint = getString(R.string.description)
                textChangeListener(viewModel::setDescription)
            }

            city.editText.apply {
                hint = getString(R.string.city)
                textChangeListener(viewModel::setCity)
            }
            company.editText.apply {
                hint = getString(R.string.company)
                textChangeListener(viewModel::setCompany)
            }
            gender.editText.apply {
                hint = getString(R.string.gender)
                textChangeListener(viewModel::setGender)
            }
            phone.editText.apply {
                hint = getString(R.string.phone)
                textChangeListener(viewModel::setPhone)
            }
        }
    }

    private fun EditText.textChangeListener(setter: (String) -> Unit) {
        addTextChangedListener {
            setter(text.toString())
        }
    }

    private fun render(state: EditScreenState) {
        when (state) {
            is EditScreenState.CurrentData -> {
                binding.progressBar.isVisible = false
                setupRealFields(state.userInfo)
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


    private fun setupRealFields(userData: UserInfoEntity){
        binding.apply {
            if(userData.name.isNotEmpty()) name.editText.hint = userData.name
            if(userData.description.isNotEmpty()) description.editText.hint = userData.description
            if(userData.city.isNotEmpty()) city.editText.hint = userData.city
            if(userData.company.isNotEmpty()) company.editText.hint = userData.company
            if(userData.gender.isNotEmpty()) gender.editText.hint = userData.gender
            if(userData.phone.isNotEmpty()) phone.editText.hint = userData.phone
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

          /*  birthday.setEndIconOnClickListener {
                setupDatePicker()
            } */

            saveButton.setOnClickListener {
                viewModel.tryToSaveInfo()
            }
        }
    }

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


            //binding.birthday.editText?.setText(selectedDate)

        }
        DatePickerDialog(
            requireContext(),
            dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    companion object {
        private var DATE_PATTERN = "dd.MM.yyyy"
        private const val EDIT_NAME = "edit name"
        private const val EDIT_GENDER = "edit gender"
        private const val EDIT_DIAGNOSIS = "edit diagnosis"
        private const val EDIT_REQUEST = "edit request"
        private const val SUCCESS = "Successfully"
    }
}
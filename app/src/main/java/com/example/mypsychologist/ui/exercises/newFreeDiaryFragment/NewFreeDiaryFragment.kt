package com.example.mypsychologist.ui.exercises.newFreeDiaryFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentNewFreeDiaryBinding
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity
import com.example.mypsychologist.extensions.bounce
import com.example.mypsychologist.extensions.expand
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.exercises.newFreeDiaryFragment.NewFreeDiaryScreenState
import com.example.mypsychologist.presentation.exercises.newFreeDiaryFragment.NewFreeDiaryViewModel
import com.example.mypsychologist.ui.core.autoCleared
import com.example.mypsychologist.ui.exercises.newCbtDiaryFragment.FragmentNewCBTDiary
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class NewFreeDiaryFragment : Fragment() {
    private var binding: FragmentNewFreeDiaryBinding by autoCleared()

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: NewFreeDiaryViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[NewFreeDiaryViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewFreeDiaryBinding.inflate(layoutInflater)

        val sdf = SimpleDateFormat("d MMMM", Locale.getDefault())
        binding.includeToolbar.toolbar.title = sdf.format(Date())
        val stf = SimpleDateFormat("EEEE, H:mm", Locale.getDefault())
        val formattedDate = stf.format(Date())
        val finalDate = formattedDate.replaceFirstChar {
            it.titlecase(Locale.getDefault())
        }
        binding.dayTime.text = finalDate

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {

        binding.field.addTextChangedListener {
            binding.saveButton.isClickable = it.toString().isNotEmpty()
        }

        binding.saveButton.setOnClickListener {
            val date = requireArguments().getString(DATE)
            viewModel.addDiary(NewFreeDiaryEntity(binding.field.text.toString()), date)
        }

        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.KPTDiaryTv.setOnClickListener {
            findNavController().navigate(R.id.fragment_new_diary,
                bundleOf(
                    FragmentNewCBTDiary.EXERCISE_ID to requireArguments().getString(KPT_ID).toString()
                )
            )
        }
    }

    private fun render(state: NewFreeDiaryScreenState) {
        when (state) {

            is NewFreeDiaryScreenState.Error -> {
                requireContext().showToast(state.msg)
                Log.e("Error", state.msg)
            }
            is NewFreeDiaryScreenState.Init -> {}
            NewFreeDiaryScreenState.Loading -> renderLoading()
            NewFreeDiaryScreenState.Success -> renderRequest()
            NewFreeDiaryScreenState.Content -> renderContent()
        }
    }

    private fun renderContent() {
        binding.field.bounce()
    }

    private fun renderLoading() {
        binding.progressCircular.isVisible = true
        binding.progressCircular.scaleX = 0f
        binding.progressCircular.scaleY = 0f
        binding.progressCircular.expand()
    }

    private fun renderRequest() {
        findNavController().popBackStack()
    }


    companion object{
        const val KPT_ID = "kpt_id"
        const val DATE = "date"
    }
}
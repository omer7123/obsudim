package com.example.mypsychologist.ui.exercises.cbt

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.NavbarHider
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentNewFreeDiaryBinding
import com.example.mypsychologist.domain.entity.FreeDiaryEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.exercises.NewFreeDiaryScreenState
import com.example.mypsychologist.presentation.exercises.NewFreeDiaryViewModel
import com.example.mypsychologist.presentation.exercises.NewThoughtDiaryScreenState
import com.example.mypsychologist.presentation.exercises.NewThoughtDiaryViewModel
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NewFreeDiaryFragment : Fragment() {
    private var binding: FragmentNewFreeDiaryBinding by autoCleared()

    private var navbarHider: NavbarHider? = null

    @Inject
    lateinit var vmFactory: NewFreeDiaryViewModel.Factory
    private val viewModel: NewFreeDiaryViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)

        if (context is NavbarHider) {
            navbarHider = context
            navbarHider!!.setNavbarVisibility(false)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewFreeDiaryBinding.inflate(layoutInflater)
//        binding.includeToolbar.toolbar.title = getString(R.string.free_diary)

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
            viewModel.tryToSaveDiary(FreeDiaryEntity(binding.field.text.toString()))
        }
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.KPTDiaryTv.setOnClickListener {

            findNavController().navigate(R.id.fragment_new_diary)
        }
    }

    private fun render(state: NewFreeDiaryScreenState) {
        when (state) {
            is NewFreeDiaryScreenState.RequestResult -> {
                renderRequest(state.success)
            }

            is NewFreeDiaryScreenState.Error -> {
                requireContext().showToast(getString(R.string.db_error))
            }

            is NewFreeDiaryScreenState.Init -> {}
        }
    }

    private fun renderRequest(isSuccess: Boolean) {

        when {
            !isSuccess -> {
                requireContext().showToast(getString(R.string.db_error))
            }

            !isNetworkConnect() -> {
                Snackbar.make(
                    binding.constraint,
                    R.string.save_after_connect,
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.go) {
                    findNavController().popBackStack()
                }.show()
            }

            else -> {
                findNavController().popBackStack()
                requireContext().showToast(getString(R.string.success))
            }
        }

    }
    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }
}
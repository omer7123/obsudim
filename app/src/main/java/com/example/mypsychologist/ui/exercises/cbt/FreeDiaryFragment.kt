package com.example.mypsychologist.ui.exercises.cbt

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentFreeDiaryBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.extensions.toDp
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.exercises.FreeDiariesViewModel
import com.example.mypsychologist.presentation.exercises.ThoughtDiariesScreenState
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class FreeDiaryFragment : Fragment() {
    private var binding: FragmentFreeDiaryBinding by autoCleared()
    private lateinit var adapter: MainAdapter

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: FreeDiariesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FreeDiariesViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFreeDiaryBinding.inflate(layoutInflater)

        setupToolbarAndFAB()
        setupAdapter()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        initView()
        return binding.root
    }

    private fun initView() {
        binding.line.post {
            val lineBottom = binding.line.bottom
            val includeTop = binding.include.toolbar.bottom

            val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(binding.bottomSheet)
            bottomSheetBehavior.peekHeight = lineBottom

            val displayMetrics = DisplayMetrics()
            val windowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)

            bottomSheetBehavior.maxHeight = displayMetrics.heightPixels - includeTop

            bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            bottomSheetBehavior.peekHeight = lineBottom
                        }
                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadDiaries()
    }

    private fun setupToolbarAndFAB() {
        binding.include.toolbar.apply {
            title = getString(R.string.free_diary)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }

        binding.newDiaryFab.setOnClickListener {
            findNavController().navigate(R.id.newFreeDiaryFragment,
                bundleOf(
                    NewFreeDiaryFragment.KPT_ID to requireArguments().getString(NewFreeDiaryFragment.KPT_ID).toString()
                )
            )
        }
    }

    private fun setupAdapter() {
        adapter = MainAdapter().apply {
            addDelegate(
                RecordDelegate { id ->
               /*     findNavController().navigate(
                        R.id.fragment_diary,
                        bundleOf(
                            FragmentThoughtDiary.ID to id,
                            ExercisesFragment.CLIENT_ID to clientId
                        )
                    ) */
                }
            )

            binding.recordsRw.adapter = this
            binding.recordsRw.layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun render(it: ThoughtDiariesScreenState) {
        when (it) {
            is ThoughtDiariesScreenState.Data -> renderData(it.records)

            is ThoughtDiariesScreenState.Init -> {}
            is ThoughtDiariesScreenState.Loading -> {
                binding.apply {
                    binding.progressBar.isVisible = true
                    titleEmptyTv.isVisible = false
                    descTv.isVisible = false
                    recordsRw.isVisible = false
                    bottomHandle.isVisible = false
                }
            }
            is ThoughtDiariesScreenState.Error -> {
                binding.progressBar.isVisible = false
                requireContext().showToast(getString(R.string.network_error))
            }
        }
    }

    private fun renderData(records: HashMap<String, String>) {
        val layout = binding.descTv.layoutParams as ViewGroup.MarginLayoutParams
        val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(binding.bottomSheet)

        binding.progressBar.isVisible = false

        if (records.isNotEmpty()) {
            binding.apply {
                recordsRw.isVisible = true
                bottomHandle.isVisible = true
                descTv.isVisible = true

                layout.topMargin = 30
                descTv.layoutParams = layout
                bottomSheetBehavior.isDraggable = true
            }
            adapter.submitList(records.toDelegateItems())

        }else {
            binding.apply {
                titleEmptyTv.isVisible = true
                descTv.isVisible = true

                layout.topMargin = 20.toDp(requireContext())
                descTv.layoutParams = layout

                bottomSheetBehavior.isDraggable = false
            }
        }
    }
}
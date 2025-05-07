package com.example.mypsychologist.ui.diagnostics.historyTestFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestHistoryBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.extensions.toPercent
import com.example.mypsychologist.presentation.diagnostics.historyTestFragment.TestHistoryScreenState
import com.example.mypsychologist.presentation.diagnostics.historyTestFragment.TestHistoryViewModel
import com.example.mypsychologist.presentation.diagnostics.testResultFragment.TestResultViewModel
import com.example.mypsychologist.ui.core.autoCleared
import com.example.mypsychologist.ui.diagnostics.testResultFragment.TestResultFragment
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.RadarEntry
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.math.max


class FragmentTestHistory : Fragment() {

    private var binding: FragmentTestHistoryBinding by autoCleared()

    @Inject
    lateinit var vmFactory: TestHistoryViewModel.Factory
    private val viewModel: TestHistoryViewModel by viewModels { vmFactory }

    private var adapter = TestDateSwitchAdapter(this::onSwitch, this::clickListener)
    private fun onSwitch(testResultId: String, checked: Boolean) {
        viewModel.getResultTestById(testResultId, checked)
    }

    private fun clickListener(testResultId: String) {
        findNavController().navigate(
            R.id.testResultFragment, bundleOf(
                TestResultFragment.TEST_TITLE to binding.title.text,
                TestResultViewModel.TEST_RESULT_ID to testResultId
            )
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().diagnosticComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestHistoryBinding.inflate(inflater, container, false)

        binding.title.text = requireArguments().getString(TEST_TITLE)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.screenState.value == TestHistoryScreenState.Init)
            viewModel.loadHistory(requireArguments().getString(TEST_ID).toString())
    }

    private fun render(state: TestHistoryScreenState) {
        when (state) {
            is TestHistoryScreenState.Loading -> {
                if (isNetworkConnect()) {
                    binding.radar.isVisible = false
                    binding.progressBar.isVisible = true
                } else {
                    binding.includePlaceholder.layout.isVisible = true
                }
            }

            is TestHistoryScreenState.Data -> {
                binding.progressBar.isVisible = false
                binding.includePlaceholder.layout.isVisible = false
                if (state.results.isNotEmpty()) {
                    if (binding.testDatesSwitchesRw.adapter == null || adapter.currentList != state.results)
                        setupAdapter(state.results, state.checkedTests.map { it.testResultId })
                    if (state.checkedTests.isEmpty()) {
                        setupRadar(state.results)
                    } else {
                        setupRadarWitDifferentTestResult(state.checkedTests)
                    }
                } else
                    showPlaceholderForEmptyList()
            }

            is TestHistoryScreenState.Error -> {
                binding.progressBar.isVisible = false
                requireContext().showToast(getString(R.string.db_error))
            }

            is TestHistoryScreenState.Init -> {}
        }
    }

    private fun setupRadarWitDifferentTestResult(checkedTests: Set<TestResultsGetEntity>) {
        val list = checkedTests.toList()
        binding.radar.setTouchEnabled(false)
        binding.radar.marker = null

        val listScale: ArrayList<ArrayList<RadarEntry>> = ArrayList()
        val listScaleNormalize: ArrayList<ArrayList<RadarEntry>> = ArrayList()

        val labelsScale: ArrayList<String> = ArrayList()
        var maxValue = 0f

        for (scale in list[0].scaleResults) {
            labelsScale.add(scale.scaleTitle)
        }
        for (res in list) {
            val listRes: ArrayList<RadarEntry> = ArrayList()
            val listResNormalize: ArrayList<RadarEntry> = ArrayList()
            for (scale in res.scaleResults) {
                listRes.add(RadarEntry(scale.score))
                listResNormalize.add(RadarEntry(scale.score.toPercent(scale.maxScore)))
                maxValue = max(scale.maxScore.toFloat(), maxValue)
            }
            listScale.add(listRes)
            listScaleNormalize.add(listResNormalize)
        }
        for ((i, v) in labelsScale.withIndex()) {
            val words = v.split(" ").filter { it.isNotEmpty() }
            if (words.size > 1) {
                labelsScale[i] = words.joinToString(separator = "") { it[0].uppercase() }
            }
            if (words.size == 1 && v.length > 10) {
                val strBuilder = StringBuilder()
                for ((indexOfSimvol, sim) in v.withIndex()) {
                    if (indexOfSimvol <= 5)
                        strBuilder.append(sim)
                    else if (indexOfSimvol == 6) strBuilder.append("-")
                    else if (indexOfSimvol >= v.length - 3) strBuilder.append(sim)
                }
                labelsScale[i] = strBuilder.toString()
            }
        }

        binding.radar.updateData(listScaleNormalize, labelsScale, 100f, false)
        binding.radar.marker = null
    }

    private fun setupRadar(list: List<TestResultsGetEntity>) {
        binding.radar.isVisible = true
        binding.radar.setTouchEnabled(true)
        val mapScore = mutableMapOf<String, Float>()

        var maxValue = 0f
        val maxValues: ArrayList<Int> = ArrayList()
        val listScale: ArrayList<ArrayList<RadarEntry>> = ArrayList()
        val listScaleNormalize: ArrayList<ArrayList<RadarEntry>> = ArrayList()
        val labelsScale: ArrayList<String> = ArrayList()

        val listResNormalize: ArrayList<RadarEntry> = ArrayList()
        val listRes: ArrayList<RadarEntry> = ArrayList()
        val labelsForMarkerView: ArrayList<String> = ArrayList()

        for (test in list) {
            for (scale in test.scaleResults) {
                mapScore[scale.scaleTitle] =
                    mapScore.getOrDefault(scale.scaleTitle, 0f) + scale.score
                maxValue = max(maxValue, scale.maxScore.toFloat())
                maxValues.add(scale.maxScore)
            }
        }

        var i = 0
        for ((k, v) in mapScore) {
            listResNormalize.add(RadarEntry((v / list.size).toPercent(maxValues[i])))
            listRes.add(RadarEntry((v / list.size)))
            labelsScale.add(k)
            labelsForMarkerView.add(k)
            i++
        }
        listScale.add(listRes)
        listScaleNormalize.add(listResNormalize)


        for ((index, v) in labelsScale.withIndex()) {
            val words = v.split(" ").filter { it.isNotEmpty() }
            if (words.size > 1) {
                labelsScale[index] = words.joinToString(separator = "") { it[0].uppercase() }
            }
            if (words.size == 1 && v.length > 10) {
                val strBuilder = StringBuilder()
                for ((indexOfSimvol, sim) in v.withIndex()) {
                    if (indexOfSimvol <= 5)
                        strBuilder.append(sim)
                    else if (indexOfSimvol == 6) strBuilder.append("-")
                    else if (indexOfSimvol >= v.length - 3) strBuilder.append(sim)
                }
                labelsScale[index] = strBuilder.toString()
            }
        }
        binding.radar.updateData(listScaleNormalize, labelsScale, 100f)
        val mv: MarkerView =
            CustomMarkerView(requireContext(), R.layout.custom_marker_view, labelsForMarkerView, maxValues, listScale)
        mv.chartView = binding.radar // For bounds control
        binding.radar.marker = mv // Set the marker to the chart
    }

    private fun setupAdapter(list: List<TestResultsGetEntity>, selectedItems: List<String>) {
        adapter = TestDateSwitchAdapter(this::onSwitch, this::clickListener, selectedItems.toList())

        binding.testDatesSwitchesRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.testDatesSwitchesRw.adapter = adapter

        adapter.submitList(list)
    }

    private fun showPlaceholderForEmptyList() {
        binding.radar.isVisible = false
        binding.testDatesSwitchesRw.isVisible = false

        binding.includePlaceholder.apply {
            image.setImageResource(R.drawable.ic_import_contacts)
            title.text = getString(R.string.nothing)
            text.text = getString(R.string.no_tests)
            layout.isVisible = true
        }


    }

    companion object {
        const val TEST_ID = "test_id"
        const val TEST_TITLE = "test_title"
    }
}
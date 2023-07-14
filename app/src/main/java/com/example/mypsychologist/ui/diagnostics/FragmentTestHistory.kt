package com.example.mypsychologist.ui.diagnostics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.databinding.FragmentTestHistoryBinding
import com.example.mypsychologist.domain.entity.TestResultEntity

class FragmentTestHistory : Fragment() {

    private lateinit var binding: FragmentTestHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestHistoryBinding.inflate(inflater, container, false)

        binding.title.text = getString(requireArguments().getInt(TEST_TITLE_ID))

        setupAdapter(
            listOf(
                TestResultEntity(3, "Нормальное состояние", 4385302443L),
                TestResultEntity(5, "Нормальное состояние", 4385402443L),
            )
        )

        return binding.root
    }

    private fun setupAdapter(list: List<TestResultEntity>) {

        binding.resultsRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = TestHistoryAdapter(list)
        }
    }

    companion object {
        fun newInstance(testTitleId: Int) =
            FragmentTestHistory().apply {
                arguments = bundleOf(TEST_TITLE_ID to testTitleId)
            }
        const val TEST_TITLE_ID = "test title id"
    }
}
package com.example.mypsychologist.ui.diagnostics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestsBinding
import com.example.mypsychologist.domain.entity.TestCardEntity
import com.example.mypsychologist.domain.entity.TestGroupEntity
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.MainAdapter

class FragmentTests : Fragment() {

    private lateinit var binding: FragmentTestsBinding
    private lateinit var mainAdapter: MainAdapter

    private var test: List<DelegateItem> = listOf(
        TestGroupDelegateItem(0, TestGroupEntity("Депрессия", R.drawable.ic_help)),
        TestGroupDelegateItem(0, TestGroupEntity("Тревожность", R.drawable.ic_help)),
        TestGroupDelegateItem(0, TestGroupEntity("Акцентуации", R.drawable.ic_help)),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestsBinding.inflate(inflater, container, false)

        setupAdapter()

        mainAdapter.submitList(test)

        return binding.root
    }

    private fun setupAdapter() {
        val onTestGroupClick: (String, Boolean) -> Unit = { title, isChecked ->
            if (isChecked) {
                val newList = test.map { it }.toMutableList()
                val position =
                    newList.indexOf(newList.find {
                        (it.content() is TestGroupEntity) && (it.content() as TestGroupEntity).title == title }
                    )
                newList.add(
                    position + 1,
                    TestDelegateItem(
                        0,
                        TestCardEntity("Тест", "Тут будет описание", "А тут пока не будет описания")
                    )
                )
                test = newList
                mainAdapter.submitList(test)
            } else {
                test = test.filterIsInstance<TestGroupDelegateItem>()
                mainAdapter.submitList(test)
            }
        }
        val onTestClick: (String, String) -> Unit = { title, description ->

        }

        mainAdapter = MainAdapter().apply {
            addDelegate(TestGroupDelegate(onTestGroupClick))
            addDelegate(TestDelegate(onTestClick))
        }

        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.test_group_item_divider))

        binding.testsRw.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }
    }
}
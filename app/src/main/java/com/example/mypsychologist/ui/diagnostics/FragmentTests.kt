package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil3.compose.AsyncImage
import com.example.compose.AppTheme
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestsBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.diagnostics.TestsScreenState
import com.example.mypsychologist.presentation.diagnostics.TestsViewModel
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.theme.textBlackColor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentTests : Fragment() {

    private var binding: FragmentTestsBinding by autoCleared()

    @Inject
    lateinit var vmFactory: TestsViewModel.Factory
    private val viewModel: TestsViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().diagnosticComponent().create().inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestsBinding.inflate(inflater, container, false)

        binding.include.apply {
            toolbar.title = getString(R.string.diagnostics)
            profileIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_profile)
            }
            psychologistsIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_psychologists_with_tasks)
            }
        }

        viewModel.screenState.flowWithLifecycle(lifecycle).onEach { state ->
            renderState(state)
        }.launchIn(lifecycleScope)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTests()
    }

    private fun renderState(state: TestsScreenState) {
        when (state) {
            is TestsScreenState.Content -> setupAdapter(state.data)
            is TestsScreenState.Error -> {
                binding.progressCircular.isVisible = false
                binding.testsRw.isVisible = false
                requireContext().showToast(state.msg)
            }

            TestsScreenState.Initial -> {}
            TestsScreenState.Loading -> {
                binding.testsRw.isVisible = false
                binding.progressCircular.isVisible = true
            }
        }
    }

    private fun setupAdapter(data: List<TestEntity>) {

        binding.progressCircular.isVisible = false
        binding.testsRw.isVisible = true

        val onTestClick: (String, String, String) -> Unit = { testId, description, testTitle ->
            DiagnosticDialogFragment.newInstance(testId, testTitle, description)
                .show(childFragmentManager, DiagnosticDialogFragment.TAG)

        }

        val mockData: List<TestEntity> = listOf(
            TestEntity(
                "Определение уровня тревожности ", "Fsdf", "fdsf", "fdfd", "fdfd"
            ),
            TestEntity("Tete", "Fsdf", "fdsf", "fdfd", "fdfd"),
            TestEntity("Tete", "Fsdf", "fdsf", "fdfd", "fdfd"),
            TestEntity("Tete", "Fsdf", "fdsf", "fdfd", "fdfd"),
        )

        binding.testsRw.setContent {
            AppTheme {
                TestsScreen(data = mockData)
            }
        }
//        binding.testsRw.apply {
//            layoutManager = GridLayoutManager(requireContext(), 2)
//
//            adapter = MainAdapter().apply {
//                addDelegate(TestDelegate(onTestClick))
//                submitList(data)
//            }
//        }
    }
}

@Composable
fun TestsScreen(data: List<TestEntity>) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp)
    ) {
        items(data) {
            TestItem(it){
                //CLick
            }
        }
    }
}

@Composable
fun TestItem(item: TestEntity, onItemClick: () -> Unit) {
    Column(modifier = Modifier
        .clip(shape = RoundedCornerShape(12.dp))
        .clickable {
            onItemClick()
        }) {

        AsyncImage(
            model = item.linkToPicture,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_tracker_mood_practice),
            error = painterResource(id = R.drawable.ic_diary_practice),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .aspectRatio(1 / 0.89f)
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = item.title,
            style = MaterialTheme.typography.bodyMedium,
            color = textBlackColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestItemPreview() {
    TestItem(TestEntity("Rere", "dsasda", "ds", "Ds", "sd")){

    }
}
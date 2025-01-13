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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.compose.AppTheme
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestsBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.diagnostics.TestsScreenState
import com.example.mypsychologist.presentation.diagnostics.TestsViewModel
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.theme.textBlackColor
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
            toolbar.title = getString(R.string.tests)
            profileIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_profile)
            }
            psychologistsIcon.setOnClickListener {
                findNavController().navigate(R.id.fragment_psychologists_with_tasks)
            }
        }

        binding.testsRw.setContent {
            AppTheme {
                TestsScreen(viewModel = viewModel, childFragmentManager)
            }
        }

        viewModel.getTests()

        return binding.root
    }
}

@Composable
fun TestsScreen(viewModel: TestsViewModel, childFragmentManager: FragmentManager) {
    val viewState = viewModel.screenState.collectAsState()
    when (val state = viewState.value) {
        is TestsScreenState.Content -> TestsContent(data = state.data) { test ->
            DiagnosticDialogFragment.newInstance(test.testId, test.title, test.description)
                .show(childFragmentManager, DiagnosticDialogFragment.TAG)
        }

        is TestsScreenState.Error -> {}
        TestsScreenState.Initial -> {}
        TestsScreenState.Loading -> {}
    }

}

@Composable
fun TestsContent(data: List<TestEntity>, onItemClick: (TestEntity) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp)
    ) {
        items(data) {
            TestItem(it) { test ->
                onItemClick(test)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TestsContentPreview() {
    TestsContent(
        data = listOf(
            TestEntity(
                "Test1",
                "Desc1",
                "desc1Short",
                "ds",
                "https://shapka-youtube.ru/wp-content/uploads/2024/07/kartinka-na-avatarki-so-lvom.jpg"
            ),
            TestEntity(
                "Test4",
                "Desc1",
                "desc1Short",
                "ds",
                "https://shapka-youtube.ru/wp-content/uploads/2024/07/kartinka-na-avatarki-so-lvom.jpg"
            ),
            TestEntity(
                "Test3",
                "Desc1",
                "desc1Short",
                "ds",
                "https://shapka-youtube.ru/wp-content/uploads/2024/07/kartinka-na-avatarki-so-lvom.jpg"
            ),
            TestEntity(
                "Test4",
                "Desc1",
                "desc1Short",
                "ds",
                "https://shapka-youtube.ru/wp-content/uploads/2024/07/kartinka-na-avatarki-so-lvom.jpg"
            ),
        )
    ) {}
}

@Composable
fun TestItem(item: TestEntity, onItemClick: (TestEntity) -> Unit) {
    Column(modifier = Modifier
        .clip(shape = RoundedCornerShape(12.dp))
        .clickable {
            onItemClick(item)
        }) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.linkToPicture)
                .build(),
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
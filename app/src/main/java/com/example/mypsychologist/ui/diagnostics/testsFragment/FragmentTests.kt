package com.example.mypsychologist.ui.diagnostics.testsFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
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
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentTestsBinding
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.diagnostics.testsFragment.TestsScreenState
import com.example.mypsychologist.presentation.diagnostics.testsFragment.TestsViewModel
import com.example.mypsychologist.ui.core.autoCleared
import com.example.mypsychologist.ui.core.composeComponents.PlaceholderError
import com.example.mypsychologist.ui.theme.AppTheme
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

        is TestsScreenState.Content ->{
            TestsContent(data = state.data) { test ->

            DiagnosticDialogFragment.newInstance(test.testId, test.title, test.description)
                .show(childFragmentManager, DiagnosticDialogFragment.TAG)
        }
            }

        is TestsScreenState.Error -> {
            PlaceholderError()
        }

        TestsScreenState.Initial -> {}
        TestsScreenState.Loading -> {
            TestsLoading()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TestsLoading() {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp)
    ) {
        items(6) {
            SkeletonItem()
        }
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
fun TestItem(item: TestEntity, onItemClick: (TestEntity) -> Unit) {
    Column(modifier = Modifier
        .clickable {
            onItemClick(item)
        }) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(item.linkToPicture).build(),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(color = AppTheme.colors.loading),
            error = painterResource(id = R.drawable.ic_diary_practice),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .aspectRatio(1 / 0.89f)
        )

        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = item.title,
            style = AppTheme.typography.bodyM,
            color = AppTheme.colors.primaryText,
        )
    }
}

@Composable
fun SkeletonItem() {
    Column {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .aspectRatio(1 / 0.89f)
                .background(color = Color.LightGray.copy(alpha = 0.3f))

        )

        Box(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .height(16.dp)
                .fillMaxWidth(0.6f)
                .clip(RoundedCornerShape(4.dp))
                .background(color = Color.LightGray.copy(alpha = 0.3f))

        )
    }
}


@Composable
@Preview(showBackground = true)
fun TestsContentPreview() {
    val data: MutableList<TestEntity> = MutableList(6) {
        TestEntity(
            "Test1",
            "Desc1",
            "desc1Short",
            "ds",
            "https://shapka-youtube.ru/wp-content/uploads/2024/07/kartinka-na-avatarki-so-lvom.jpg"
        )
    }

    TestsContent(
        data = data
    ) {}
}
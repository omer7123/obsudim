package com.example.mypsychologist.ui.education.educationTopicsFragment

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentEducationTopicsBinding
import com.example.mypsychologist.domain.entity.educationEntity.TopicEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.education.educationTopicsFragment.EducationTopicsViewModel
import com.example.mypsychologist.presentation.education.educationTopicsFragment.TopicsScreenState
import com.example.mypsychologist.ui.core.autoCleared
import com.example.mypsychologist.ui.core.composeComponents.PlaceholderError
import com.example.mypsychologist.ui.core.composeComponents.SkeletonItem
import com.example.mypsychologist.ui.education.educationFragment.EducationFragment
import com.example.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class EducationTopicsFragment : Fragment() {

    private var binding: FragmentEducationTopicsBinding by autoCleared()

    @Inject
    lateinit var vmFactory: EducationTopicsViewModel.Factory
    private val viewModel: EducationTopicsViewModel by viewModels { vmFactory }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().educationComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEducationTopicsBinding.inflate(inflater, container, false)

        binding.include.apply {
            toolbar.title = getString(R.string.psychoeducation)
            profileIcon.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_education_topics_to_profile_graph)
            }
        }

        setupAdapter()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTopics()
    }

    private fun setupAdapter() {
        binding.topicsRw.setContent {
            AppTheme {
                EducationTopicsScreen(viewModel)
            }
        }
    }

    @Composable
    fun EducationTopicsScreen(
        viewModel: EducationTopicsViewModel
    ) {
        val viewState = viewModel.screenState.collectAsState()
        when (val state = viewState.value) {
            is TopicsScreenState.Content -> TopicsContent(data = state.data) { topic ->
                findNavController().navigate(
                    R.id.action_fragment_education_topics_to_reading_theory_graph, bundleOf(
                        EducationFragment.TOPIC_TAG to topic.id,
                    )
                )
            }
            is TopicsScreenState.Error -> {
                PlaceholderError()
            }
            TopicsScreenState.Loading -> {
                TopicsLoading()
            }
            TopicsScreenState.Initial -> {}
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TopicsLoading() {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp)
        ) {
            items(7) {
                SkeletonItem()
            }
        }
}

@Composable
fun TopicsContent(data: List<TopicEntity>, onItemClick: (TopicEntity) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(1),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp)
    ) {
        items(data) {
            TopicItem(it) { test ->
                onItemClick(test)
            }
        }
    }
}

@Composable
fun TopicItem(item: TopicEntity, onItemClick: (TopicEntity) -> Unit) {
    Column(modifier = Modifier
        .clip(shape = RoundedCornerShape(12.dp))
        .clickable {
            onItemClick(item)
        }) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(item.educationMaterials[0].linkToPicture).build(),
            contentDescription = item.theme,
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(color = AppTheme.colors.loading),
            error = painterResource(id = R.drawable.ic_diary_practice),
            modifier = Modifier
                .aspectRatio(1 / 0.8f)
                .clip(shape = RoundedCornerShape(12.dp))


        )

        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = item.theme,
            style = AppTheme.typography.bodyXLBold,
            color = AppTheme.colors.primaryText,
        )
    }
}
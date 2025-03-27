package com.example.mypsychologist.ui.exercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentExercisesBinding
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.core.BaseStateUI
import com.example.mypsychologist.presentation.exercises.exercisesFragment.REBTViewModel
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.core.DiaryTextButton
import com.example.mypsychologist.ui.core.PlaceholderError
import com.example.mypsychologist.ui.exercises.cbt.exerciseResultsFragment.FragmentDiaries
import com.example.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class FragmentExercises : Fragment() {

    private var binding: FragmentExercisesBinding by autoCleared()

    private var kptExercise: ExerciseEntity? = null

    @Inject
    lateinit var vmFactory: REBTViewModel.Factory
    private val viewModel: REBTViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesBinding.inflate(inflater, container, false)

        binding.include.profileIcon.setOnClickListener {
            findNavController().navigate(R.id.fragment_profile)
        }
        binding.content.setContent {
            AppTheme {
                SetupMainContent(onThinkDiaryClick = {
                    kptExercise?.let { kpt ->
                        findNavController().navigate(
                            R.id.fragment_diaries, bundleOf(
                                FragmentDiaries.EXERCISE_ID to kpt.id,
                                FragmentDiaries.EXERCISE_TITLE to kpt.title,
                                FragmentDiaries.EXERCISE_DESCRIPTION to kpt.description,
                                FragmentDiaries.IMAGE to kpt.linkToPicture,
                            )
                        )
                    }
                }, onFreeDiaryClick = {
                    findNavController().navigate(
                        R.id.freeDiaryTrackerMoodFragment,
                    )
                },
                    )

            }
        }
        return binding.root
    }

    @Composable
    private fun SetupMainContent(
        onThinkDiaryClick: () -> Unit,
        onFreeDiaryClick: () -> Unit,
    ) {
        val viewState = viewModel.screenState.collectAsState()
        when (val res = viewState.value) {
            is BaseStateUI.Content -> {
                kptExercise = res.data.find { it.title == "КПТ-дневник" }

                RenderContent(
                    onThinkDiaryClick = { onThinkDiaryClick() },
                    onFreeDiaryClick = { onFreeDiaryClick() },
                    modifier = Modifier.background(color = AppTheme.colors.screenBackground),
                    onDefinitionGroupProblemClick = {findNavController().navigate(R.id.action_fragment_exercises_to_definitionProblemGroupExerciseFragment)}
                )
            }

            is BaseStateUI.Error -> PlaceholderError()
            is BaseStateUI.Initial -> Unit
            is BaseStateUI.Loading -> RenderLoading()
        }
    }

    @Composable
    private fun RenderContent(
        onThinkDiaryClick: () -> Unit,
        onFreeDiaryClick: () -> Unit,
        modifier: Modifier = Modifier,
        onDefinitionGroupProblemClick: () -> Unit,
    ) {
        LazyVerticalGrid(
            modifier = modifier.padding(horizontal = 16.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(vertical = 30.dp),
        ) {
            item(span = {
                GridItemSpan(2)
            }) {
                Text(
                    text = stringResource(id = R.string.daily_tasks),
                    style = AppTheme.typography.titleXS,
                    color = AppTheme.colors.primaryText
                )
            }

            item(span = {
                GridItemSpan(2)
            }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            }, indication = null
                        ) {
                            onThinkDiaryClick()
                        },
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        painter = painterResource(id = R.drawable.ic_kpt_card),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth
                    )

                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    Text(
                        text = stringResource(id = R.string.cbt_diary),
                        style = AppTheme.typography.bodyXLBold,
                        color = AppTheme.colors.primaryText
                    )
                }
            }

            item(span = {
                GridItemSpan(2)
            }) {
                DiaryTextButton(modifier = Modifier.padding(vertical = 20.dp)) {
                    onFreeDiaryClick()
                }
            }

     /*       item(span = {
                GridItemSpan(2)
            }) {
                Text(
                    text = stringResource(id = R.string.problem_work),
                    style = AppTheme.typography.titleXS,
                    color = AppTheme.colors.primaryText
                )
            } */

            item{
                ExerciseItem(item = ExerciseEntity(
                    id="1",
                    title = "Определение групп \n" +
                            "(категорий) проблем",
                    description = "",
                    linkToPicture = "https://xn--b1afb6bcb.xn--c1ajjlbco7a.xn----gtbbcb4bjf2ak.xn--p1ai/exercise/images_exercise/Определение_групп_проблем.png",
                    closed = false
                )) {
                    onDefinitionGroupProblemClick()
                }
            }
//            items(value) {
//                Spacer(modifier = Modifier.padding(top = 20.dp))
//                when (it.closed) {
//                    true -> ExerciseItemClosed(item = it)
//                    false -> ExerciseItem(item = it) { item ->
//                        onExerciseClick(item)
//                    }
//                }
//            }
        }
    }


    @Composable
    private fun ExerciseItem(item: ExerciseEntity, onItemClick: (ExerciseEntity) -> Unit) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onItemClick(item)
            }) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(item.linkToPicture).build(),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(AppTheme.colors.loading),
                error = painterResource(id = R.drawable.ic_diary_practice),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .aspectRatio(1 / 0.89f)
            )

            Text(
                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
                text = item.title,
                style = AppTheme.typography.bodyM,
                color = AppTheme.colors.primaryText,
            )
        }
    }

    @Composable
    private fun ExerciseItemClosed(item: ExerciseEntity) {
        Column {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .aspectRatio(1 / 0.89f)
                    .background(color = AppTheme.colors.primaryText)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(item.linkToPicture)
                        .build(),
                    alpha = 0.5f,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_tracker_mood_practice),
                    error = painterResource(id = R.drawable.ic_diary_practice),
                )
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "",
                    tint = AppTheme.colors.screenBackground

                )
            }

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = item.title,
                style = AppTheme.typography.bodyM,
                color = AppTheme.colors.primaryText,
            )
        }
    }

    @Composable
    private fun RenderLoading() {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun RenderContent_Preview() {
        Scaffold {
            AppTheme {
                RenderContent(modifier = Modifier.padding(it), onThinkDiaryClick = {}, onFreeDiaryClick = {}, onDefinitionGroupProblemClick = {})
            }
        }
    }
}
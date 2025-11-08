package com.obsudim.mypsychologist.ui.exercises.exercisesFragment

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.databinding.FragmentExercisesBinding
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.exercises.exercisesFragment.ExercisesScreenState
import com.obsudim.mypsychologist.presentation.exercises.exercisesFragment.ExercisesViewModel
import com.obsudim.mypsychologist.ui.core.autoCleared
import com.obsudim.mypsychologist.ui.exercises.recordsExerciseFragment.RecordsExerciseFragment
import com.obsudim.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class ExercisesFragment : Fragment() {

    private var binding: FragmentExercisesBinding by autoCleared()

    private var kptExercise: ExerciseEntity? = null

    @Inject
    lateinit var vmFactory: ExercisesViewModel.Factory
    private val viewModel: ExercisesViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesBinding.inflate(inflater, container, false)

        binding.include.profileIcon.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_exercises_to_profile_graph)
        }
        binding.content.setContent {
            AppTheme {
                SetupMainContent(
                    onFreeDiaryClick = {
                        findNavController().navigate(
                            R.id.action_fragment_exercises_to_freeDiaryTrackerMoodFragment,
                        )
                    },
                    onDefinitionGroupProblemClick = {
                        findNavController().navigate(
                            R.id.fragment_diaries,
                            bundleOf(
                                RecordsExerciseFragment.EXERCISE_ID to "DPG_ID",
                                RecordsExerciseFragment.EXERCISE_TITLE to "Определение проблемы, постановка цели",
                                RecordsExerciseFragment.EXERCISE_DESCRIPTION to "А теперь, давайте обозначим четкую форму своей проблемы, это поможет понять её суть и определить, к чему хотите прийти.",
                                RecordsExerciseFragment.IMAGE to "DS",
                            )
                        )
                    }
                )
            }
        }
        return binding.root
    }

    @Composable
    private fun SetupMainContent(
        onFreeDiaryClick: () -> Unit,
        onDefinitionGroupProblemClick: () -> Unit,
    ) {
        val viewState = viewModel.screenState.collectAsState()

        when (val res = viewState.value) {
            is ExercisesScreenState.Content -> {
                RenderContent(
                    onFreeDiaryClick = { onFreeDiaryClick() },
                    modifier = Modifier.background(
                        color = AppTheme.colors.screenBackground
                    ),
                )
            }

            is ExercisesScreenState.Error -> Unit
            ExercisesScreenState.Init -> Unit
            ExercisesScreenState.Loading -> RenderLoading()

        }
    }

    @Composable
    private fun RenderContent(
        onFreeDiaryClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_kpt_card),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White.copy(alpha = 1f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Изучаем себя",
                        style = AppTheme.typography.titleCygreSemiBold,
                        fontSize = 28.sp,
                        color = AppTheme.colors.primaryText,
                        modifier = Modifier.padding(bottom = 30.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.colors.primaryText,
                                contentColor = AppTheme.colors.primaryTextInvert
                            ),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {},
                            modifier = Modifier.weight(1f)

                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_screp),
                                contentDescription = null,
                                modifier = Modifier.padding(vertical = 16.dp).padding(end = 8.dp)
                            )
                            Text(
                                text = stringResource(R.string.start),
                                style = AppTheme.typography.titleCygreSemiBold,
                                fontSize = 16.sp,
                            )
                        }
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.colors.primaryText,
                                contentColor = AppTheme.colors.primaryTextInvert
                            ),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_screp),
                                contentDescription = null,
                                modifier = Modifier.padding(vertical = 16.dp).padding(end = 8.dp)
                            )
                            Text(
                                text = stringResource(R.string.cbt_diary_new_name),
                                style = AppTheme.typography.titleCygreSemiBold,
                                fontSize = 14.sp,
                            )
                        }
                    }
                }


            }
            LazyRow(
                modifier = Modifier.weight(1f)
            ) {
                item{
                    Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }, indication = null
                                ) {

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
                                text = stringResource(id = R.string.cbt_diary_new_name),
                                style = AppTheme.typography.bodyXLBold,
                                color = AppTheme.colors.primaryText
                            )
                        }
                }

            }

//                LazyVerticalGrid(
//                    modifier = modifier.padding(horizontal = 16.dp),
//                    columns = GridCells.Fixed(2),
//                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(20.dp),
//                    contentPadding = PaddingValues(vertical = 30.dp),
//                ) {
//                    item(span = {
//                        GridItemSpan(2)
//                    }) {
//                        Text(
//                            text = stringResource(id = R.string.daily_tasks),
//                            style = AppTheme.typography.titleXS,
//                            color = AppTheme.colors.primaryText
//                        )
//                    }
//
//                    item(span = {
//                        GridItemSpan(2)
//                    }) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable(
//                                    interactionSource = remember {
//                                        MutableInteractionSource()
//                                    }, indication = null
//                                ) {
//
//                                },
//                        ) {
//                            Image(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .wrapContentHeight(),
//                                painter = painterResource(id = R.drawable.ic_kpt_card),
//                                contentDescription = "",
//                                contentScale = ContentScale.FillWidth
//                            )
//
//                            Spacer(modifier = Modifier.padding(top = 10.dp))
//
//                            Text(
//                                text = stringResource(id = R.string.cbt_diary_new_name),
//                                style = AppTheme.typography.bodyXLBold,
//                                color = AppTheme.colors.primaryText
//                            )
//                        }
//                    }
//
//                    item(span = {
//                        GridItemSpan(2)
//                    }) {
//                        DiaryTextButton(modifier = Modifier.padding(vertical = 20.dp)) {
//                            onFreeDiaryClick()
//                        }
//                    }
//                }

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
                RenderContent(
                    modifier = Modifier.padding(it),
                    onFreeDiaryClick = {},
                )
            }
        }
    }
}
package com.obsudim.mypsychologist.ui.exercises.exercisesFragment

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.obsudim.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class ExercisesFragment : Fragment() {

    private var binding: FragmentExercisesBinding by autoCleared()

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
                    viewModel,
                    onClickExercise = {}
                )
            }
        }
        return binding.root
    }

    @Composable
    private fun SetupMainContent(
        onFreeDiaryClick: () -> Unit,
        viewModel: ExercisesViewModel,
        onClickExercise: (String) -> Unit
    ) {
        val viewState = viewModel.screenState.collectAsState()

        when (val res = viewState.value) {
            is ExercisesScreenState.Content -> {
                RenderContent(
                    onFreeDiaryClick = { onFreeDiaryClick() },
                    onClickExercise = onClickExercise,
                    data = res.data,
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
        onClickExercise: (String) -> Unit,
        data: List<ExerciseEntity>,
    ) {
        Column(modifier = Modifier.fillMaxSize().background(color = AppTheme.colors.screenBackground)) {
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
                                    AppTheme.colors.screenBackground.copy(alpha = 1f)
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
                            onClick = {onFreeDiaryClick()},
                            modifier = Modifier.weight(1f)

                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_screp),
                                contentDescription = null,
                                modifier = Modifier.padding(vertical = 16.dp).padding(end = 8.dp)
                            )
                            Text(
                                text = stringResource(R.string.notes),
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
                                fontSize = 16.sp,
                            )
                        }
                    }
                }


            }
            LazyRow(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(data) { item ->
                    CardExercise(item){
                        onClickExercise
                    }
                }
            }
        }
    }

    @Composable
    private fun CardExercise(
        item: ExerciseEntity,
        onClickExercise: (id: String) -> Unit
    ) {
        Box(
            modifier = Modifier
                .width(180.dp)
                .clip(RoundedCornerShape(28.dp))
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = null
                ) {
                    onClickExercise(item.id)
                },
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_kpt_card),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = item.title,
                style = AppTheme.typography.titleCygreSemiBold,
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)

            )
        }
    }

    @Composable
    private fun RenderLoading() {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Preview(showBackground = true)
    @Composable
    private fun RenderContent_Preview() {
        Scaffold {
            AppTheme {
                RenderContent(
                    onFreeDiaryClick = {},
                    onClickExercise = {},
                    data = listOf(
                        ExerciseEntity(
                            id = "",
                            title = "Определение групп проблем",
                            linkToPicture = "g[f[gf",
                            open = true
                        ),
                        ExerciseEntity(
                            id = "",
                            title = "Определение проблемы,\n" +
                                    "постановка цели",
                            linkToPicture = "g[f[gf",
                            open = true
                        )
                    ),
                )
            }
        }
    }
}
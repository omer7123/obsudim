package com.obsudim.mypsychologist.ui.exercises.exercisesHostFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseAllResultEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseInfoPreviewEntity
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.di.MultiViewModelFactory
import com.obsudim.mypsychologist.presentation.exercises.exercisesHostFragment.ExerciseHostScreenState
import com.obsudim.mypsychologist.presentation.exercises.exercisesHostFragment.ExercisesHostViewModel
import com.obsudim.mypsychologist.ui.core.composeComponents.PlaceholderError
import com.obsudim.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class ExercisesHostFragment: Fragment() {

    companion object {
        private const val EXERCISE_ID = "EXERCISE_ID"
    }

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: ExercisesHostViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ExercisesHostViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getHistory(requireArguments().getString(EXERCISE_ID)!!)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            AppTheme{
                OnboardingExerciseScreen(
                    onBackClick = {findNavController().popBackStack()},
                    viewModel
                )
            }
        }
    }

    @Composable
    fun OnboardingExerciseScreen(
        onBackClick: () -> Unit,
        viewModel: ExercisesHostViewModel,
    ) {
        val viewState = viewModel.screenState.collectAsState()
        when(val state = viewState.value){
            is ExerciseHostScreenState.Content -> {
                OnboardingExerciseContent(
                    state.data,
                    state.history,
                    onBackClick,
                )
            }
            ExerciseHostScreenState.Error -> {
                PlaceholderError()
            }
            ExerciseHostScreenState.Initial -> Unit
            ExerciseHostScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }

    }

    @Composable
    fun OnboardingExerciseContent(
        data: ExerciseInfoPreviewEntity,
        history: List<ExerciseAllResultEntity>,
        onBackClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.screenBackground)
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.ic_exercise_host_bg),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()
                )
                Image(
                    painter = painterResource(R.drawable.ic_arrow_back_white),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 32.dp)
                        .clickable { onBackClick() }
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    AppTheme.colors.screenBackground.copy(alpha = 1f)
                                )
                            )
                        )
                )
                Text(
                    text = data.title,
                    style = AppTheme.typography.titleCygreSemiBold,
                    color = AppTheme.colors.primaryText,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 16.dp)
                )
            }

            Column(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = AppTheme.colors.screenBackground)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.primaryText,
                        contentColor = AppTheme.colors.primaryTextInvert
                    ),
                    onClick = {},
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_screp),
                        contentDescription = null,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.start),
                        style = AppTheme.typography.titleCygreSemiBold,
                        fontSize = 16.sp,
                    )
                }

                when {
                    history.isEmpty() -> RenderInfo(
                        data, modifier = Modifier
                            .padding(top = 20.dp)
                            .padding(horizontal = 16.dp)
                    )

                    else -> RenderHistory(
                        history, modifier = Modifier
                            .padding(top = 20.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun RenderHistory(history: List<ExerciseAllResultEntity>, modifier: Modifier) {
        Column(
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.history),
                style = AppTheme.typography.titleCygreSemiBold,
                fontSize = 26.sp,
                color = AppTheme.colors.primaryText
            )

            Spacer(modifier = Modifier.padding(top = 20.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(history) { item ->
                    CardHistory(item)
                }
            }
        }
    }

    @Composable
    private fun CardHistory(item: ExerciseAllResultEntity) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = AppTheme.colors.tertiaryBackground,
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {

            Text(
                text = item.preview,
                style = AppTheme.typography.bodyLBold,
                fontSize = 16.sp,
                color = AppTheme.colors.primaryText,
            )

            Spacer(modifier = Modifier.padding(top = 6.dp))

            Text(
                text = item.date,
                style = AppTheme.typography.bodyM,
                fontSize = 14.sp,
                color = AppTheme.colors.primaryText,
            )
        }
    }

    @Composable
    private fun RenderInfo(data: ExerciseInfoPreviewEntity, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .background(
                    color = AppTheme.colors.tertiaryBackground,
                    shape = RoundedCornerShape(28.dp)
                )
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.allows),
                style = AppTheme.typography.titleCygreSemiBold,
                color = AppTheme.colors.primaryText,
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 20.dp)
            )
            Text(
                text = data.description,
                style = AppTheme.typography.bodyM,
                color = AppTheme.colors.primaryText,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 20.dp, top = 2.dp)
            )
        }

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .background(
                        color = AppTheme.colors.tertiaryBackground,
                        shape = RoundedCornerShape(28.dp)
                    )
            ) {
                Text(
                    text = stringResource(R.string.time_to_read, data.timeToRead),
                    style = AppTheme.typography.titleCygreSemiBold,
                    color = AppTheme.colors.primaryText,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp)
                )
                Text(
                    text = stringResource(R.string.to_pass),
                    style = AppTheme.typography.bodyM,
                    color = AppTheme.colors.primaryText,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 20.dp, top = 2.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(start = 16.dp)
                    .background(
                        color = AppTheme.colors.tertiaryBackground,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.count_questions, data.questionsCount),
                    style = AppTheme.typography.titleCygreSemiBold,
                    color = AppTheme.colors.primaryText,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp)
                )
                Text(
                    text = stringResource(R.string.with_open_answers),
                    style = AppTheme.typography.bodyM,
                    color = AppTheme.colors.primaryText,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 20.dp, top = 2.dp)
                )
            }
        }
    }

    @Composable
    @Preview
    fun OnboardingExerciseScreenPreview(){
        AppTheme {
            OnboardingExerciseContent(
                data = ExerciseInfoPreviewEntity(
                    id = "fd",
                    title = "Что вас расстраивает?",
                    description = "понять, какие скрытые эмоции\n" +
                            "о травматическом событии не выходят из головы",
                    timeToRead = 14,
                    questionsCount = 3
                ),
                history = listOf(
                    ExerciseAllResultEntity(
                        id = "TODO()",
                        exerciseId = "TODO()",
                        date = "12-02-2025",
                        preview = "Вота как как то"
                    ),
                    ExerciseAllResultEntity(
                        id = "TODO()",
                        exerciseId = "TODO()",
                        date = "12-02-2025",
                        preview = "Вота как как то"
                    ),
                    ExerciseAllResultEntity(
                        id = "TODO()",
                        exerciseId = "TODO()",
                        date = "12-02-2025",
                        preview = "Вота как как то"
                    ),
                ),
                onBackClick = {}
            )
        }
    }
}
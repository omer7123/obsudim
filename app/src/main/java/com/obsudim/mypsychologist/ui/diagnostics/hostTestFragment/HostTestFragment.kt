package com.obsudim.mypsychologist.ui.diagnostics.hostTestFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestResultsGetEntity
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.di.MultiViewModelFactory
import com.obsudim.mypsychologist.presentation.diagnostics.hostTestFragment.HostTestScreenState
import com.obsudim.mypsychologist.presentation.diagnostics.hostTestFragment.HostTestViewModel
import com.obsudim.mypsychologist.ui.core.composeComponents.PlaceholderError
import com.obsudim.mypsychologist.ui.diagnostics.passingTestFragment.PassingTestFragment
import com.obsudim.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class HostTestFragment : Fragment() {

    companion object {
        const val TEST_ID = "TEST_ID"
        const val TEST_RESULT_ID = "testResultId"
    }

    @Inject
    lateinit var vmFactory: MultiViewModelFactory
    private val viewModel: HostTestViewModel by lazy {
        ViewModelProvider(this, vmFactory)[HostTestViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().diagnosticComponent().create().inject(this)
    }

    override fun onStart() {
        super.onStart()
        viewModel.initData(requireArguments().getString(TEST_ID)!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                HostTestScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        findNavController().popBackStack()
                    },
                    onStartClick = {
                        val testId = requireArguments().getString(TEST_ID)

                        findNavController().navigate(
                            R.id.passing_test_graph, bundleOf(
                                PassingTestFragment.TEST_ID to testId,
                            )
                        )
                    },
                    onItemClick = { testResId ->
                        findNavController().navigate(
                            R.id.result_test_graph,
                            bundleOf(
                                TEST_RESULT_ID to testResId
                            )
                        )
                    }
                )
            }
        }
    }

    @Composable
    private fun HostTestScreen(
        viewModel: HostTestViewModel,
        onBackClick: () -> Unit,
        onStartClick: () -> Unit,
        onItemClick: (String) -> Unit,
    ) {
        val uiState = viewModel.screenState.collectAsState()
        when (val data = uiState.value) {
            is HostTestScreenState.Content -> HostTestContent(
                title = data.data.title,
                description = data.data.description,
                history = data.data.history,
                onBackClick = onBackClick,
                onStartClick = onStartClick,
                onItemClick = { onItemClick(it) }
            )

            HostTestScreenState.Error -> {
                PlaceholderError()
            }
            HostTestScreenState.Initial -> {
                Box(modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            HostTestScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

    @Composable
    private fun HostTestContent(
        title: String,
        description: String,
        history: List<TestResultsGetEntity>,
        onBackClick: () -> Unit,
        onStartClick: () -> Unit,
        onItemClick: (String) -> Unit,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.screenBackground)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
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
                    text = title,
                    style = AppTheme.typography.titleCygreSemiBold,
                    color = AppTheme.colors.primaryText,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 16.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(color = AppTheme.colors.screenBackground)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.primaryText,
                        contentColor = AppTheme.colors.primaryTextInvert
                    ),
                    onClick = { onStartClick() },
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
                        description, modifier = Modifier
                            .padding(top = 20.dp)
                            .padding(horizontal = 16.dp)
                    )

                    else -> RenderHistory(
                        history,
                        onItemClick = { onItemClick(it) },
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun RenderInfo(description: String, modifier: Modifier = Modifier) {
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
                text = description,
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
//                Text(
//                    text = stringResource(R.string.time_to_read, data.timeToRead),
//                    style = AppTheme.typography.titleCygreSemiBold,
//                    color = AppTheme.colors.primaryText,
//                    fontSize = 22.sp,
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp)
//                        .padding(top = 20.dp)
//                )
//                Text(
//                    text = stringResource(R.string.to_pass),
//                    style = AppTheme.typography.bodyM,
//                    color = AppTheme.colors.primaryText,
//                    fontSize = 14.sp,
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp)
//                        .padding(bottom = 20.dp, top = 2.dp)
//                )
            }

//            Column(
//                modifier = Modifier
//                    .padding(top = 20.dp)
//                    .padding(start = 16.dp)
//                    .background(
//                        color = AppTheme.colors.tertiaryBackground,
//                        shape = RoundedCornerShape(28.dp)
//                    )
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = stringResource(R.string.count_questions, data.questionsCount),
//                    style = AppTheme.typography.titleCygreSemiBold,
//                    color = AppTheme.colors.primaryText,
//                    fontSize = 22.sp,
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp)
//                        .padding(top = 20.dp)
//                )
//                Text(
//                    text = stringResource(R.string.with_open_answers),
//                    style = AppTheme.typography.bodyM,
//                    color = AppTheme.colors.primaryText,
//                    fontSize = 14.sp,
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp)
//                        .padding(bottom = 20.dp, top = 2.dp)
//                )
//            }
        }
    }

    @Composable
    private fun RenderHistory(
        history: List<TestResultsGetEntity>,
        onItemClick: (String) -> Unit,
        modifier: Modifier
    ) {
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
                    CardHistory(
                        item = item,
                        onItemClick = { onItemClick(it) }
                    )
                }
            }
        }
    }

    @Composable
    private fun CardHistory(
        item: TestResultsGetEntity,
        onItemClick: (String) -> Unit,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = AppTheme.colors.tertiaryBackground,
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .clickable {
                    onItemClick(item.testResultId)
                }
        ) {

            Text(
                text = "",
                style = AppTheme.typography.bodyLBold,
                fontSize = 16.sp,
                color = AppTheme.colors.primaryText,
            )

            Spacer(modifier = Modifier.padding(top = 6.dp))

            Text(
                text = item.datetime,
                style = AppTheme.typography.bodyM,
                fontSize = 14.sp,
                color = AppTheme.colors.primaryText,
            )
        }
    }

}
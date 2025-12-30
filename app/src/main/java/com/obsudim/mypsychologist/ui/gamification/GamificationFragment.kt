package com.obsudim.mypsychologist.ui.gamification

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.databinding.FragmentGamificationBinding
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.CurrentScoreEntity
import com.obsudim.mypsychologist.domain.entity.gamificationEntity.WeeklyScoresEntity
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserDataEntity
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.gamification.GamificationScreenState
import com.obsudim.mypsychologist.presentation.gamification.GamificationViewModel
import com.obsudim.mypsychologist.ui.core.autoCleared
import com.obsudim.mypsychologist.ui.core.composeComponents.IconTextButton
import com.obsudim.mypsychologist.ui.core.composeComponents.PlaceholderError
import com.obsudim.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class GamificationFragment : Fragment() {

    private var binding: FragmentGamificationBinding by autoCleared()

    @Inject
    lateinit var vmFactory: GamificationViewModel.Factory
    private val viewModel: GamificationViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().gamificationComponent().create().inject(this)
        viewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamificationBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }


        binding.composeView.setContent {
            AppTheme {
                GamificationContent(viewModel)
            }
        }



        return binding.root
    }

    @Composable
    private fun GamificationContent(viewModel: GamificationViewModel) {
        val viewState = viewModel.screenState.collectAsState()
        when (val result = viewState.value) {
            is GamificationScreenState.Content -> {

                GamificationRenderContent(
                    currentScore = result.currentScore,
                    weeklyScores = result.weeklyScores,
                    userInfo = result.userInfo
                )
                Log.d("aaaa", "yes")
            }

            is GamificationScreenState.Error -> {
                Log.e("GamificationScreenState.Error", result.msg)
                PlaceholderError()
            }

            GamificationScreenState.Initial -> Unit
            GamificationScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

    @Composable
    private fun GamificationRenderContent(
        currentScore: CurrentScoreEntity,
        weeklyScores: WeeklyScoresEntity,
        userInfo: UserDataEntity
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                style = AppTheme.typography.titleCygreFont,
                text = userInfo.username,
                color = AppTheme.colors.primaryText,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Text(
                style = AppTheme.typography.bodyXLBold,
                text = "Ментальное здоровье",
                color = AppTheme.colors.primaryText,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            ProgressBarScore(
                currentValue = currentScore.score,
                maxValue = 40,
                modifier = Modifier.padding(bottom = 10.dp),
                minLeftWidth = 64.dp
            )
            val leftScore = 40 - currentScore.score
            Text(
                style = AppTheme.typography.bodyLBold,
                text = "Нужно набрать еще $leftScore очков",
                color = AppTheme.colors.secondaryText,
                modifier = Modifier.padding(bottom = 40.dp)
            )
            Text(
                style = AppTheme.typography.bodyXLBold,
                text = "Непрерывная забота",
                color = AppTheme.colors.primaryText,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            val scoreValues = weeklyScores.scores.map { it.score }
            Graph(
                scores = scoreValues
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
//                IconTextButton(
//                    modifier = Modifier.padding(top = 20.dp),
//                    height = 56.dp,
//                    width = 182.dp,
//                    icon = painterResource(id = R.drawable.ic_achievements),
//                    horizontalPadding = 24.dp,
//                    verticalPadding = 16.dp,
//                    text = "Достижения",
//                    onClick = {  }
//                )
                IconTextButton(
                    modifier = Modifier.padding(top = 20.dp),
                    height = 56.dp,
                    width = 380.dp,
                    icon = painterResource(id = R.drawable.ic_settings),
                    horizontalPadding = 31.dp,
                    verticalPadding = 16.dp,
                    text = "Настройки",
                    onClick = { findNavController().navigate(R.id.action_gamificationFragment2_to_fragment_profile) }
                )
            }
        }
    }

    companion object {
        fun newInstance(): GamificationFragment {
            return GamificationFragment()
        }
    }
}
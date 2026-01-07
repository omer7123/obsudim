package com.obsudim.mypsychologist.ui.core.composeComponents.exercisesComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.ui.theme.AppTheme

@Composable
fun SliderItem(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = AppTheme.typography.titleXS,
                color = AppTheme.colors.primaryText,
            )
        }

        Box(
            modifier = Modifier
                .width(380.dp)
                .height(140.dp)
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(color = AppTheme.colors.tertiaryBackground)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
            ) {
                Slider(
                    value = value,
                    onValueChange = onValueChange,
                    colors = SliderDefaults.colors(
                        thumbColor = AppTheme.colors.primaryBackground,
                        activeTrackColor = AppTheme.colors.primaryBackground,
                        inactiveTrackColor = AppTheme.colors.primaryTextInvert,
                    ),
                    steps = 0,
                    valueRange = 0f..100f,
                    modifier = Modifier.fillMaxWidth()
                )

                val moodText = when (value.toInt()) {
                    in 0..20 -> stringResource(R.string.terrible_mood)
                    in 21..40 -> stringResource(R.string.bad_mood)
                    in 41..59 -> stringResource(R.string.normal_mood)
                    in 60..79 -> stringResource(R.string.good_mood)
                    in 80..100 -> stringResource(R.string.super_mood)
                    else -> stringResource(R.string.normal_mood)
                }

                Text(
                    text = moodText,
                    style = AppTheme.typography.bodyS,
                    color = AppTheme.colors.primaryTextInvert,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .background(
                            color = AppTheme.colors.primaryBackground,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SliderItemPreview() {
    AppTheme {
            SliderItem(
                title = "Как ваше настроение?",
                value = 50f,
                onValueChange = {}
            )
    }
}
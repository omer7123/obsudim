package com.example.mypsychologist.ui.core

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity
import com.example.mypsychologist.ui.theme.AppTheme
import com.example.mypsychologist.ui.theme.AppTheme.typography

@Preview(showBackground = true)
@Composable
fun PlaceholderError(
    @StringRes text: Int = R.string.unknown_error_placeholder
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_place),
            contentDescription = stringResource(
                id = R.string.db_error
            )
        )
        Text(
            modifier = Modifier
                .padding(top = 100.dp)
                .padding(horizontal = 16.dp),
            text = stringResource(id = text),
            style = typography.bodyL

        )
    }
}

@Composable
fun RecordItem(
    item: RecordExerciseEntity, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.tertiaryBackground,
                shape = RoundedCornerShape(12.dp)
            )

    ) {
        Text(
            text = item.title,
            style = typography.bodyMBold,
            color = AppTheme.colors.primaryText,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
        Text(
            text = item.date,
            style = typography.bodyM,
            color = AppTheme.colors.primaryText,
            modifier = Modifier.padding(start = 16.dp, top = 6.dp, bottom = 16.dp)
        )
    }
}

@Composable
fun IndicatorBottomSheet(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .size(width = 40.dp, height = 4.dp)
            .background(
                color = AppTheme.colors.tertiaryBackground,
                shape = RoundedCornerShape(12.dp)
            )
    )
}


@Preview(showBackground = false)
@Composable
fun PrimaryTextField_Preview(){
    AppTheme {
    }
}



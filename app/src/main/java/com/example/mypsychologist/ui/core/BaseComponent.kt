package com.example.mypsychologist.ui.core

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mypsychologist.R
import com.example.mypsychologist.ui.theme.AppTheme

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
            style = AppTheme.typography.bodyLarge

        )
    }
}


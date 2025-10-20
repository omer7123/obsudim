package com.obsudim.mypsychologist.ui.core.composeComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.ui.theme.AppTheme

@Composable
fun PrimaryTextButton(
    textString: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.primaryBackground,
                shape = RoundedCornerShape(12.dp)
            ),
        contentPadding = PaddingValues(vertical = 13.dp),
        onClick = { onClick() },
        enabled = !isLoading
    ) {
        if (!isLoading) {
            Text(
                text = textString,
                style = AppTheme.typography.bodyMBold,
                color = AppTheme.colors.primaryTextInvert
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp,
                color = AppTheme.colors.screenBackground
            )
        }
    }
}

@Composable
fun SecondaryTextButton(
    textString: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.secondaryBackground,
                shape = RoundedCornerShape(12.dp)
            ),
        contentPadding = PaddingValues(vertical = 13.dp),
        onClick = {
            onClick()
        },
    ) {
        Text(
            text = textString,
            style = AppTheme.typography.bodyMBold,
            color = AppTheme.colors.primaryText
        )
    }
}

@Composable
fun DiaryTextButton(
    modifier: Modifier = Modifier,
    textString: String = stringResource(id = R.string.free_diary),
    onClick: () -> Unit
){
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.primaryBackground,
                shape = RoundedCornerShape(12.dp)
            ),
        contentPadding = PaddingValues(horizontal = 16.5.dp, vertical = 13.dp),
        onClick = { onClick() },
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier,
                    text = textString,
                    style = AppTheme.typography.bodyXLBold,
                    color = AppTheme.colors.primaryTextInvert
                )
                Icon(
                    modifier = Modifier.size(34.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    tint = AppTheme.colors.primaryTextInvert,
                    contentDescription = ""
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DiaryTextButton_Preview(){
    AppTheme {
        DiaryTextButton() {
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryTextButton_Preview(){
    AppTheme {
        PrimaryTextButton(
            textString = stringResource(id = R.string.mail),
            isLoading = false,
            onClick = {},
            modifier = Modifier.padding(vertical = 30.dp, horizontal = 10.dp)
        )
    }
}
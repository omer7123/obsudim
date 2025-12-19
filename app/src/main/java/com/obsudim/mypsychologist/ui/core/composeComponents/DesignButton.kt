package com.obsudim.mypsychologist.ui.core.composeComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun TotalTextButton(
    textString: String,
    onClick: () -> Unit,
    bgColor: Color = AppTheme.colors.primaryText,
    modifier: Modifier = Modifier,
){
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 30.dp)
            .background(
                color = bgColor,
                shape = RoundedCornerShape(28.dp)
            ),
        contentPadding = PaddingValues(vertical = 13.dp),
        onClick = {
            onClick()
        },
    ) {
        Text(
            text = textString,
            style = AppTheme.typography.titleCygreSemiBold,
            color = AppTheme.colors.primaryTextInvert,
            fontSize = 16.sp
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

@Composable
fun IconTextButton(
    text: String,
    onClick: () -> Unit,
    icon: Any,
    modifier: Modifier = Modifier,
    width: Dp? = null,
    height: Dp,
    iconSize: Dp = 24.dp,
    iconColor: Color = AppTheme.colors.primaryText,
    backgroundColor: Color = AppTheme.colors.tertiaryBackground,
    textStyle: TextStyle = AppTheme.typography.bodyLBold,
    textColor: Color = AppTheme.colors.primaryText,
    cornerRadius: Dp = 28.dp,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 16.dp,
    spacing: Dp = 10.dp
) {
    val buttonModifier = if (width != null) {
        modifier
            .width(width)
            .height(height)
    } else {
        modifier
            .height(height)
    }

    TextButton(
        modifier = buttonModifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor),
        contentPadding = PaddingValues(
            horizontal = horizontalPadding,
            vertical = verticalPadding
        ),
        onClick = {
            onClick()
        },
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (icon) {
                is Painter -> {
                    Icon(
                        painter = icon,
                        contentDescription = text,
                        modifier = Modifier.size(iconSize),
                        tint = iconColor
                    )
                }
                is ImageVector -> {
                    Icon(
                        imageVector = icon,
                        contentDescription = text,
                        modifier = Modifier.size(iconSize),
                        tint = iconColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(spacing))

            Text(
                text = text,
                style = textStyle,
                color = textColor
            )
        }
    }
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

@Preview(showBackground = true)
@Composable
private fun SecondaryTextButton_Preview(){
    AppTheme {
        SecondaryTextButton(
            textString = stringResource(id = R.string.mail),
            onClick = {},
            modifier = Modifier.padding(vertical = 30.dp, horizontal = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TotalTextButton_Preview(){
    AppTheme {
        TotalTextButton(
            textString = stringResource(id = R.string.mail),
            onClick = {},
            modifier = Modifier.padding(vertical = 30.dp, horizontal = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IconTextButton_Preview(){
    AppTheme {
        IconTextButton(
            modifier = Modifier.padding(top = 20.dp),
            height = 56.dp,
            width = 190.dp,
            icon = painterResource(id = R.drawable.ic_settings),
            horizontalPadding = 31.dp,
            verticalPadding = 16.dp,
            text = "Настройки",
            onClick = {  }
        )
    }
}
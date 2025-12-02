package com.obsudim.mypsychologist.ui.core.composeComponents.exercisesComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obsudim.mypsychologist.ui.theme.AppTheme

@Composable
fun TextItemPrimary(
    title: String,
    text: String,
) {
    TextItem(
        title = title,
        text = text,
        modifier = Modifier,
        height = 110,
        textColor = AppTheme.colors.primaryText,
        backgroundTextFieldColor = AppTheme.colors.screenBackground,
        backgroundColor = AppTheme.colors.tertiaryBackground,
    )
}

@Composable
fun TextItemDefault(
    title: String,
    text: String,
) {
    TextItem(
        title = title,
        text = text,
        modifier = Modifier,
        height = 110,
        textColor = AppTheme.colors.primaryText,
        backgroundTextFieldColor = AppTheme.colors.tertiaryBackground,
        backgroundColor = AppTheme.colors.screenBackground,
    )
}

@Composable
private fun TextItem(
    title: String,
    text: String,
    height: Int,
    backgroundTextFieldColor: Color = AppTheme.colors.navBackground,
    textColor: Color = AppTheme.colors.primaryTextInvert,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    modifier
        .fillMaxWidth()
        .sizeIn(minHeight = height.dp)
        .background(
            color = backgroundTextFieldColor,
            shape = RoundedCornerShape(28.dp)
        )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(horizontal = 16.dp, vertical = 30.dp)
    ) {
        Text(
            text = title,
            style = AppTheme.typography.titleCygreSemiBold,
            fontSize = 26.sp,
            color = AppTheme.colors.primaryText
        )

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(minHeight = height.dp)
                .background(
                    color = backgroundTextFieldColor,
                    shape = RoundedCornerShape(28.dp)
                ),
        ) {
            Text(
                text = text,
                color = textColor,
                style = AppTheme.typography.bodyM,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun TextItemDefault_Preview(){
    AppTheme{
        TextItemDefault(
            title = "Опишите ситуацию\n" +
                    "в максимальных деталях",
            text = "Что-то произошло Что-то произошло Что-то произошло Что-то произошло Что-то произошло Что-то произошло"
        )
    }
}
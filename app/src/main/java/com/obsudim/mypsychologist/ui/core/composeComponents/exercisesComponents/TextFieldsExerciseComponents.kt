package com.obsudim.mypsychologist.ui.core.composeComponents.exercisesComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obsudim.mypsychologist.ui.core.composeComponents.TransparentPrimaryTextField
import com.obsudim.mypsychologist.ui.theme.AppTheme

@Composable
fun TextInputItem(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    height: Int = 140,
    backgroundColor: Color = AppTheme.colors.navBackground,
    textColor: Color = AppTheme.colors.secondaryBackground
) {
    val defaultModifier = modifier
        .fillMaxWidth()
        .height(height.dp)
        .padding(horizontal = 16.dp)
        .background(
            color = backgroundColor,
            shape = RoundedCornerShape(28.dp)
        )
        .padding(
            vertical = 16.dp,
            horizontal = 20.dp
        )

    TransparentPrimaryTextField(
        field = text,
        placeHolderText = placeholder,
        onFieldChange = onTextChange,
        modifier = defaultModifier,
        textColor = textColor
    )
}


@Preview(showBackground = true)
@Composable
fun TextInputItemBluePreview() {
    AppTheme {
        val textState = remember { mutableStateOf("") }

        TextInputItem(
            text = textState.value,
            onTextChange = { textState.value = it },
            placeholder = "Что произошло?",
            modifier = Modifier,
            height = 140,
            textColor = Color(0xFFCDD7FF),
            backgroundColor = AppTheme.colors.navBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputItemWhitePreview() {
    AppTheme {
        val textState = remember { mutableStateOf("") }

        TextInputItem(
            text = textState.value,
            onTextChange = { textState.value = it },
            placeholder = "Мне кажется....",
            modifier = Modifier,
            height = 110,
            textColor = AppTheme.colors.secondaryText,
            backgroundColor = AppTheme.colors.tertiaryBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputItemGreenPreview() {
    AppTheme {
        val textState = remember { mutableStateOf("") }

        TextInputItem(
            text = textState.value,
            onTextChange = { textState.value = it },
            placeholder = "Брать ипотеку или просить повышения...",
            modifier = Modifier,
            height = 224,
            textColor = Color(0xFF8DD9C6),
            backgroundColor = Color(0xFF015641)
        )
    }
}
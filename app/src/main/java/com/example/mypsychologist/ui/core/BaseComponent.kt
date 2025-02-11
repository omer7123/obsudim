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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun PrimaryTextField(
    field: String,
    placeHolderText: String,
    onFieldChange: (String) -> Unit,
    imeAction: ImeAction,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    modifier: Modifier = Modifier
    ) {
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.tertiaryBackground, shape = RoundedCornerShape(12.dp)
            ), value = field, onValueChange = onFieldChange, singleLine = true, placeholder = {
            Text(
                text = placeHolderText,
                style = typography.bodyM,
                color = AppTheme.colors.secondaryText
            )
        }, colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = AppTheme.colors.primaryText,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            cursorColor = AppTheme.colors.primaryText
        ), textStyle = typography.bodyM,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            capitalization = capitalization
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }
        )
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextFieldForDropMenu(
    field: String,
    placeHolderText: String,
    onFieldChange: (String) -> Unit,
    imeAction: ImeAction,
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.tertiaryBackground, shape = RoundedCornerShape(12.dp)
            ),
        value = field, onValueChange = onFieldChange, singleLine = true, placeholder = {
            Text(
                text = placeHolderText,
                style = typography.bodyM,
                color = AppTheme.colors.secondaryText
            )
        },
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = AppTheme.colors.primaryText,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            cursorColor = AppTheme.colors.primaryText
        ), textStyle = typography.bodyM,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
        ),
        readOnly = true,
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }
        )
    )
}

@Composable
fun PrimarySecurityTextField(
    field: String,
    placeHolderText: String,
    onFieldChange: (String) -> Unit,
    imeAction: ImeAction,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.tertiaryBackground,
                shape = RoundedCornerShape(12.dp)
            ),
        value = field,
        onValueChange = onFieldChange,
        singleLine = true,
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = "")
            }
        },
        visualTransformation =
            if(passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),

        placeholder = {
            Text(
                text = placeHolderText,
                style = typography.bodyM,
                color = AppTheme.colors.secondaryText
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = AppTheme.colors.primaryText,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            cursorColor = AppTheme.colors.primaryText
        ),
        textStyle = typography.bodyM,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            },
            onDone = {
                focusManager.clearFocus()
            }
        ),
    )
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
        PrimaryTextField(
            field = stringResource(id = R.string.mail),
            placeHolderText = stringResource(id = R.string.mail),
            onFieldChange = {},
            imeAction = ImeAction.Next
        )
    }
}



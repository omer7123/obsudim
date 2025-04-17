package com.example.mypsychologist.ui.core.composeComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mypsychologist.ui.theme.AppTheme

@Composable
fun PrimaryTextField(
    field: String,
    placeHolderText: String,
    onFieldChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorStr: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val focusManager: FocusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        TextField(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (errorStr.isNullOrEmpty()) {
                    AppTheme.colors.tertiaryBackground
                } else {
                    AppTheme.colors.errorContainer
                }, shape = RoundedCornerShape(12.dp)
            ),
            value = field,
            onValueChange = onFieldChange,
            singleLine = true,

            placeholder = {
                Text(
                    text = placeHolderText,
                    style = AppTheme.typography.bodyM,
                    color = AppTheme.colors.secondaryText
                )
            },
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = AppTheme.colors.primaryText,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                cursorColor = AppTheme.colors.primaryText
            ),

            textStyle = AppTheme.typography.bodyM,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }, onDone = {
                focusManager.clearFocus()
            })
        )

        if (!errorStr.isNullOrEmpty()) {
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = errorStr,
                style = AppTheme.typography.bodyM,
                color = AppTheme.colors.secondaryText
            )
        }
    }
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

    TextField(modifier = modifier
        .fillMaxWidth()
        .background(
            color = AppTheme.colors.tertiaryBackground, shape = RoundedCornerShape(12.dp)
        ), value = field, onValueChange = onFieldChange, singleLine = true, placeholder = {
        Text(
            text = placeHolderText,
            style = AppTheme.typography.bodyM,
            color = AppTheme.colors.secondaryText
        )
    }, trailingIcon = {
        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
    }, colors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = AppTheme.colors.primaryText,
        unfocusedBorderColor = Color.Transparent,
        focusedBorderColor = Color.Transparent,
        cursorColor = AppTheme.colors.primaryText
    ), textStyle = AppTheme.typography.bodyM, keyboardOptions = KeyboardOptions(
        imeAction = imeAction,
    ), readOnly = true, keyboardActions = KeyboardActions(onNext = {
        focusManager.moveFocus(FocusDirection.Down)
    })
    )
}

@Composable
fun PrimaryPickerTextField(
    field: String,
    placeHolderText: String,
    onFieldChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Default,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorStr: String? = null,
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if (errorStr.isNullOrEmpty()) {
                        AppTheme.colors.tertiaryBackground
                    } else {
                        AppTheme.colors.errorContainer
                    }, shape = RoundedCornerShape(12.dp)

                ),
            value = field,
            onValueChange = onFieldChange,
            singleLine = true,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            placeholder = {
                Text(
                    text = placeHolderText,
                    style = AppTheme.typography.bodyM,
                    color = AppTheme.colors.secondaryText
                )
            },
            readOnly = readOnly,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = AppTheme.colors.primaryText,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                cursorColor = AppTheme.colors.primaryText
            ),
            textStyle = AppTheme.typography.bodyM,
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }, onDone = {
                focusManager.clearFocus()
            }),
        )

        Spacer(modifier = Modifier.padding(top = 10.dp))

        if (!errorStr.isNullOrEmpty()) {
            Text(
                text = errorStr,
                style = AppTheme.typography.bodyM,
                color = AppTheme.colors.secondaryText
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = {
            onDateSelected(datePickerState.selectedDateMillis)
            onDismiss()
        }) {
            Text("OK")
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text("Cancel")
        }
    }) {
        DatePicker(state = datePickerState)
    }
}
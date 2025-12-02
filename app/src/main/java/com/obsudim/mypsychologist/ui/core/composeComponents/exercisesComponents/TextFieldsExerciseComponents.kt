package com.obsudim.mypsychologist.ui.core.composeComponents.exercisesComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.FieldAddableListChange
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.SectionsExerciseEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSection
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.TypeOfSectionUiRes
import com.obsudim.mypsychologist.ui.core.composeComponents.TransparentPrimaryTextField
import com.obsudim.mypsychologist.ui.theme.AppTheme

@Composable
fun TextInputItem(
    title: String,
    text: String? = null,
    onTextChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    height: Int = 140,
    backgroundTextFieldColor: Color = AppTheme.colors.navBackground,
    textFieldColor: Color = AppTheme.colors.primaryTextInvert,
    backgroundColor: Color = AppTheme.colors.primaryBackground,
    titleColor: Color = AppTheme.colors.primaryTextInvert
) {
    val defaultModifier = modifier
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
            color = titleColor
        )

        Spacer(modifier = Modifier.padding(top = 20.dp))

        TransparentPrimaryTextField(
            field = text ?: "",
            placeHolderText = placeholder,
            onFieldChange = onTextChange,
            modifier = defaultModifier,
            textColor = textFieldColor,
            singleLine = false
        )
    }
}

@Composable
fun TextInputItemDefault(
    title: String,
    text: String? = null,
    onTextChange: (String) -> Unit,
    placeholder: String = "",
) {

    TextInputItem(
        title = title,
        text = text,
        onTextChange = { onTextChange(it) },
        placeholder = placeholder,
        modifier = Modifier,
        height = 110,
        textFieldColor = AppTheme.colors.secondaryText,
        backgroundTextFieldColor = AppTheme.colors.tertiaryBackground,
        backgroundColor = AppTheme.colors.screenBackground,
        titleColor = AppTheme.colors.primaryText
    )
}

@Composable
fun AddableList(
    item: SectionsExerciseEntity,
    currVal: TypeOfSectionUiRes.AddableListUiEntity,
    onTextChange: (FieldAddableListChange) -> Unit,
    onAddItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val defaultModifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .sizeIn(minHeight = 72.dp)
        .background(
            color = AppTheme.colors.navBackground,
            shape = RoundedCornerShape(28.dp)
        )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = AppTheme.colors.primaryBackground)
    ) {
        Spacer(modifier = Modifier.padding(top = 30.dp))

        Text(
            text = item.title,
            style = AppTheme.typography.titleCygreSemiBold,
            fontSize = 26.sp,
            color = AppTheme.colors.primaryTextInvert,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.padding(top = 20.dp))


        for ((indx, currValue) in currVal.list.withIndex()) {
            TransparentPrimaryTextField(
                field = currValue,
                placeHolderText = item.placeholder,
                onFieldChange = {
                    onTextChange(
                        FieldAddableListChange(
                            item.id,
                            idItem = indx,
                            it
                        )
                    )
                },
                modifier = defaultModifier,
                textColor = AppTheme.colors.primaryTextInvert,
                singleLine = false
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colors.navBackground,
            ),
            shape = RoundedCornerShape(28.dp),
            onClick = { onAddItemClick(item.id) },
            content = {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    tint = AppTheme.colors.primaryTextInvert
                )
            }
        )

    }
}

@Composable
@Preview(showBackground = true)
fun AddableList_Preview() {
    AppTheme {
        AddableList(
            item =
                SectionsExerciseEntity(
                    id = "9a24ce66-8dd0-4008-bcf4-d06664cdd9aa",
                    title = "Минусы этого",
                    view = "primary",
                    type = TypeOfSection.AddableList,
                    placeholder = "Что произошло?",
                    prompt = "string",
                    variants = emptyList()

                ),
            currVal =
                TypeOfSectionUiRes.AddableListUiEntity(
                    idLoc = "9a24ce66-8dd0-4008-bcf4-d06664cdd9aa",
                    list = listOf("Я смогу купить авто", "Я буду счастлив")
                ),
            {},
            {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TextInputItemBluePreview() {
    AppTheme {
        val textState = remember { mutableStateOf("") }

        TextInputItem(
            title = "Опишите ситуацию\n" +
                    "в максимальных деталях",
            text = textState.value,
            onTextChange = { textState.value = it },
            placeholder = "Что произошло?",
            modifier = Modifier,
            height = 140,
            textFieldColor = Color(0xFFCDD7FF),
            backgroundTextFieldColor = AppTheme.colors.navBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputItemWhitePreview() {
    AppTheme {
        val textState = remember { mutableStateOf("") }
        TextInputItemDefault(
            title = "Какие эпизоды этой истории особенно вас расстраивают?",
            text = textState.value,
            onTextChange = { textState.value = it },
            placeholder = "Мне кажется....",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputItemGreenPreview() {
    AppTheme {
        val textState = remember { mutableStateOf("") }

        TextInputItem(
            title = "Какие эмоции и мысли возникают в такие моменты?",
            text = textState.value,
            onTextChange = { textState.value = it },
            placeholder = "Брать ипотеку или просить повышения...",
            modifier = Modifier,
            height = 224,
            textFieldColor = Color(0xFF8DD9C6),
            backgroundTextFieldColor = Color(0xFF015641)
        )
    }
}
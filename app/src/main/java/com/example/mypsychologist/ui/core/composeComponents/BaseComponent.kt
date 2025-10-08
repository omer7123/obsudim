package com.example.mypsychologist.ui.core.composeComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.diaryEntity.EmojiEntity
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu() {
    var expandedGender by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expandedGender,
        onExpandedChange = { expandedGender = it }
    ) {
//        TextFieldForDropMenu(
//            field = when (value.gender) {
//                Gender.MALE -> stringResource(id = R.string.man)
//                Gender.FEMALE -> stringResource(id = R.string.woman)
//                Gender.UNKNOWN -> stringResource(id = R.string.unknown)
//                Gender.INITIAL -> ""
//            },
//            placeHolderText = stringResource(id = R.string.gender),
//            onFieldChange = {},
//            imeAction = ImeAction.Default,
//            isExpanded = expandedGender
//        )

        ExposedDropdownMenu(
            expanded = expandedGender,
            onDismissRequest = { expandedGender = false },
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.man)) },
                onClick = {
//                    onGenderChange(Gender.MALE)
                    expandedGender = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.woman)) },
                onClick = {
//                    onGenderChange(Gender.FEMALE)
                    expandedGender = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.unknown)) },
                onClick = {
//                    onGenderChange(Gender.UNKNOWN)
                    expandedGender = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun ToolBarBasic(title: String, navigateBack: () -> Unit,){
    TopAppBar(
        title = {
            Text(title,
                style = AppTheme.typography.titleCygreFont,
                color = AppTheme.colors.navBackground)
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.frame),
                    contentDescription = ""
                )
            }
        }
    )
}

@Preview
@Composable
fun ToolbarBasicPreview() {

}

@Preview(showBackground = false)
@Composable
fun PrimaryTextField_Preview(){
    AppTheme {
    }
}

@Composable
@Preview(showBackground = true)
fun TestScreen() {
    var text by remember { mutableStateOf("Hello") }
    Text(text = text)
    text = "World"
}

@Composable
fun SkeletonItem() {
    Column {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .aspectRatio(1 / 0.89f)
                .background(color = Color.LightGray.copy(alpha = 0.3f))

        )

        Box(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .height(16.dp)
                .fillMaxWidth(0.6f)
                .clip(RoundedCornerShape(4.dp))
                .background(color = Color.LightGray.copy(alpha = 0.3f))

        )
    }
}


@Composable
fun SmileItem(bgColor: Color, emoji: EmojiEntity, onSmileClick: (Int) -> Unit){
    AppTheme {
        Box(
            modifier = Modifier
                .width(68.dp)
                .height(54.dp)
                .background(
                    color = bgColor,
                    shape = RoundedCornerShape(28.dp)
                )
                .clickable{
                    onSmileClick(emoji.id)
                }
        ) {
            Text(emoji.text, fontSize = 32.sp, modifier = Modifier.align(Alignment.Center))
        }
    }
}


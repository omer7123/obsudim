package com.obsudim.mypsychologist.ui.core.composeComponents

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun Modifier.scrollToElement(scrollState: ScrollState): Modifier {
	var elementCoordinates by remember { mutableFloatStateOf(0F) }
	val coroutineScope = rememberCoroutineScope()

	return this
		.onGloballyPositioned {
			if (elementCoordinates == 0F) {
				elementCoordinates = it.positionInParent().y
			}
		}
		.onFocusChanged { focusState ->
			coroutineScope.launch {
				if (focusState.isFocused && elementCoordinates != 0F) {
					scrollState.animateScrollTo(elementCoordinates.roundToInt())
				}
			}
		}
}
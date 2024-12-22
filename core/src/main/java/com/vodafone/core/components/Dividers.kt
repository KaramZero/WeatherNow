package com.vodafone.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
internal fun DividerVertical(
    modifier: Modifier = Modifier,
    width: Dp = 1.dp,
    color: Color = Color(0x1A707070)
) {
    Spacer(
        modifier = modifier
            .width(width)
            .fillMaxHeight()
            .background(color)
    )
}

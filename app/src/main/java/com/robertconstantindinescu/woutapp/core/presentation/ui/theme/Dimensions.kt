package com.robertconstantindinescu.woutapp.core.presentation.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    //Space dimensions
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 64.dp,
    //Icon Dimension
    val IconSizeSmall: Dp = 15.dp,
    val IconSizeMedium: Dp = 25.dp,
    val IconSizeLarge: Dp = 35.dp
)

val LocalSpacing = compositionLocalOf { Dimensions() }
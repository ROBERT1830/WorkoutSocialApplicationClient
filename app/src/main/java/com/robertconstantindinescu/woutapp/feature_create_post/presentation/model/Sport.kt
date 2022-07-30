package com.robertconstantindinescu.woutapp.feature_create_post.presentation.model

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.*
import com.robertconstantindinescu.woutapp.core.util.UiText

data class Sport(
    val type: UiText,
    @DrawableRes val drawableRes: Int,
    val isSelected: Boolean = false,
    val color: Color
) {
    companion object {
        //default sportTypes

        val defaultSportTypes = listOf(
            Sport(
                type = UiText.StringResource(R.string.swimming),
                drawableRes = R.drawable.ic_swim,
                color = BlueViolet
            ),
            Sport(
                type = UiText.StringResource(R.string.tennis),
                drawableRes = R.drawable.ic_tenis,
                color = LightGreen
            ),
            Sport(
                type = UiText.StringResource(R.string.table_tennis),
                drawableRes = R.drawable.ic_ping_pong,
                color = Beige
            ),
            Sport(
                type = UiText.StringResource(R.string.running),
                drawableRes = R.drawable.ic_run,
                color = OrangeYellow
            ),
            Sport(
                type = UiText.StringResource(R.string.gym),
                drawableRes = R.drawable.ic_gym,
                color = AquaBlue
            ),
            Sport(
                type = UiText.StringResource(R.string.volleyball),
                drawableRes = R.drawable.ic_volleyball,
                color = LightRed
            )
        )
    }
}

